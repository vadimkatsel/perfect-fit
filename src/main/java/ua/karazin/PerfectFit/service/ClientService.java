package ua.karazin.PerfectFit.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.karazin.PerfectFit.entity.*;
import ua.karazin.PerfectFit.repository.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;


@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final CourseRepository courseRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SaleSubscriptionRepository saleSubscriptionRepository;
    private final SaleCourseRepository saleCourseRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, CourseRepository courseRepository, SubscriptionRepository subscriptionRepository, SaleSubscriptionRepository saleSubscriptionRepository, SaleCourseRepository saleCourseRepository) {
        this.clientRepository = clientRepository;
        this.courseRepository = courseRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.saleSubscriptionRepository = saleSubscriptionRepository;
        this.saleCourseRepository = saleCourseRepository;
    }

    public TreeMap<LocalDate, TreeMap<String, String>> getSchedule(Integer dateIndex, Client client) {

        if (dateIndex < 0) {
            dateIndex = 0;
        }

        // Получить текущую дату
        LocalDate currentDate = LocalDate.now().plusDays(dateIndex);
        TreeMap<LocalDate, TreeMap<String, String>> schedule = new TreeMap<>();
        int countOfCols = 6;
        int startIndex = (dateIndex == 0) ? 0 : countOfCols*dateIndex;
        int endIndex = (dateIndex == 0) ? countOfCols : countOfCols * (dateIndex + 1);


        for (int i = startIndex; i < endIndex; i++) {
            LocalDate dateIterator = currentDate.plusDays(i - dateIndex);
                    List<Object[]> result = courseRepository.findCoursesByDateAndClient(dateIterator,
                            dateIterator.getDayOfWeek().getValue(), client);
                    if (!result.isEmpty()) {
                        TreeMap<String, String> timeSchedule = new TreeMap<>();
                        for (Object[] row : result) {
                            String courseName = (String) row[0];
                            LocalTime startTime = (LocalTime) row[1];


                            timeSchedule.put(startTime.toString(), courseName);

                        }
                        schedule.put(dateIterator, timeSchedule);
                    } else {
                        schedule.put(dateIterator, null);
                    }

                }

        return schedule;
    }

    public List<List<String>> getCourses (Client client) {
        List<List<String>> courses = new ArrayList<>();
        List<Object[]> result = courseRepository.getListOfCoursesForClient(client);



        for (Object[] row : result) {
            Course c = (Course)row[0];
            List<AbsenceRecordCoach> absenceRecordCoach= courseRepository.checkCoachAbsence(c);
            String absenceRecordCoachStr = "";
            for(AbsenceRecordCoach ar: absenceRecordCoach) {
                absenceRecordCoachStr += ar.getAbsenceDateBegin().toString() + " - " + ar.getAbsenceDateEnd().toString() + " ";
            }
            List<String> course = new ArrayList<>();
            course.add(c.getName());
            course.add(c.getDescription());
            course.add((String) row[1]);
            course.add(absenceRecordCoachStr);
            course.add(String.valueOf(c.getId()));
            courses.add(course);
        }
        return courses;
    }

    public void removeCourse(Long id) {
        if (id >= 0) {
            clientRepository.deleteCourse(id);
            //TODO: удалить везде saleCourse тоже
        }
    }
    public Client findClientByMail(String mail) {
        return clientRepository.findClientByMail(mail);
    }
    public SaleSubscription getSubscription(Client client) {return subscriptionRepository.getSubscriptionForClient(client);}

    public String saleSubscriptionToClient(Client client, Long idSubscription) {
        SaleSubscription saleSubscription = subscriptionRepository.getSubscriptionForClient(client);
        Subscription subscription = subscriptionRepository.getReferenceById(idSubscription);

        if(saleSubscription != null) {
            return "It seems you already have an subscription.";
        } else if(subscription == null) {
            return "It seems this subscription doesn't exist.";
        }
        client.setSubscription(subscription);
        saleSubscription = new SaleSubscription();
        saleSubscription.setBeginSubscription(LocalDate.now());
        saleSubscription.setDeadlineSubscription(LocalDate.now().plusMonths(1));
        saleSubscription.setClient(client);
        saleSubscription.setSubscription(subscription);



        saleSubscriptionRepository.save(saleSubscription);
        clientRepository.save(client);
        return "Successful operation!";
    }

    public void removeSubscription(Client client) {
        clientRepository.deleteSubscriptionForClient(client);
        clientRepository.deleteAllCoursesForClient(client);
        client.setSubscription(null);
        clientRepository.save(client);
    }

    public void updateSubscription(Client client, Long numberOfDays) {
        SaleSubscription saleSubscription = subscriptionRepository.getSubscriptionForClient(client);
        LocalDate newDeadline = saleSubscription.getDeadlineSubscription().plusMonths(numberOfDays);
        saleSubscription.setDeadlineSubscription(newDeadline);
        saleSubscriptionRepository.save(saleSubscription);
    }

    public Client updateClient(Long id, Client client) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client existingClient = optionalClient.get();
            existingClient.setFio(client.getFio());
            existingClient.setPhoneNumber(client.getPhoneNumber());
            existingClient.setMail(client.getMail());
            existingClient.setPassword(client.getPassword());
            existingClient.setSubscription(client.getSubscription());
            return clientRepository.save(existingClient);
        }
        return null;
    }

    public Long getCountOfCourses(Client client) {
        return saleCourseRepository.getCountOfCoursesForClient(client);
    }
    public String saleCourseToClient(Client client, Long idCourse) {
        Course course = courseRepository.getReferenceById(idCourse);

        if(course == null) {
            return "This course doesn't exist.";
        }
        Long countOfCourses = saleCourseRepository.getCountOfCoursesForClient(client);

        if(client.getSubscription() == null) {
            return "It seems you haven`t bought a subscription yet. This is important for joining the course.";
        }

        if(countOfCourses == client.getSubscription().getMaxCoursesCapacity()) {
            return "It seems you have already taken the maximal number of courses provided by your subscription.";
        }
        Long checkIsVisit = saleCourseRepository.checkIsVisiting(client,course);

        if(checkIsVisit != 0) {
            return "It seems you trying to buy a course, which yoг are already visiting.";
        }

        SaleCourse saleCourse = new SaleCourse();
        saleCourse.setCourse(course);
        saleCourse.setClient(client);

        saleCourseRepository.save(saleCourse);

        return "Successful operation!";
    }

    public List<Course> getListOfCourses(Client client) {
        List<Course> result = new ArrayList<>();


        return result;
    }
    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }

    public Client getAllClientsWithFIO( String fio) { return clientRepository.findClientByFio(fio);}

    public Client getClientById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.orElse(null);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }


    public void deleteClient(Long id) {clientRepository.deleteCourse(id);}

}
