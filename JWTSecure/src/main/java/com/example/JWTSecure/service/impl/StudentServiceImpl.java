package com.example.JWTSecure.service.impl;

import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.domain.*;
import com.example.JWTSecure.repo.*;
import com.example.JWTSecure.repo.impl.DetailEventCustomRepo;
import com.example.JWTSecure.repo.impl.EventCustomRepo;
import com.example.JWTSecure.repo.impl.StudentCustomRepo;
import com.example.JWTSecure.repo.impl.ViolateCustomRepo;
import com.example.JWTSecure.service.StudentService;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final PasswordEncoder passwordEncoder;
    private final StudentRepo studentRepo;
    private final UserRepo userRepo;
    private final StudentCustomRepo studentCustomRepo;
    private final EventCustomRepo eventCustomRepo;
    private final EventRepo eventRepo;
    private final ViolateRepo violateRepo;
    private final StudentInEventRepo studentInEventRepo;
    private final ViolateCustomRepo violateCustomRepo;
    private final DetailEventCustomRepo detailEventCustomRepo;

    @Override
    public StudentDTO getStudent(StudentDTO studentDTO) {
        try {
            return studentCustomRepo.getStudent(studentDTO);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SearchResultDTO<StudentDTO> getAllStudent(StudentDTO studentDTO) {
        List<StudentDTO> dataResult;
        SearchResultDTO<StudentDTO> searchResult = new SearchResultDTO<>();

        try {
            Integer totalRecord = studentCustomRepo.getTotal(studentDTO).size();
            dataResult = studentCustomRepo.doSearch(studentDTO);


            if (dataResult != null && !dataResult.isEmpty()) {
                searchResult.setCode("0");
                searchResult.setSuccess(true);
                searchResult.setTitle("Success");
                searchResult.setMessage("Success");
                searchResult.setResultData(dataResult);
                searchResult.setTotalRecordNoLimit(totalRecord);
            } else {
                searchResult.setCode("0");
                searchResult.setSuccess(false);
                searchResult.setTitle("Failure");
                searchResult.setMessage("Failure");
                searchResult.setResultData(Collections.emptyList());
                searchResult.setTotalRecordNoLimit(0);
            }
            return searchResult;
        } catch (Exception e) {
            searchResult.setCode("0");
            searchResult.setSuccess(false);
            searchResult.setTitle("Failure");
            searchResult.setMessage("Failure");
            searchResult.setResultData(Collections.emptyList());
            searchResult.setTotalRecordNoLimit(0);
            return searchResult;
        }
    }


    @Override
    public ResponseStatus addStudent(AddStudentDTO addStudentDTO) {
        User user = new User();
        Student student = new Student();
        ResponseStatus rs = new ResponseStatus();
        StringBuilder message = new StringBuilder();

        if (addStudentDTO.getCode() == null || studentRepo.findByCode(addStudentDTO.getCode()) != null) {
            rs.setMessage("Failure");
            rs.setState(false);
            return rs;
        }

        if (addStudentDTO != null) {
            if (userRepo.findByUsername(addStudentDTO.getUsername()) != null) {
                message.append("Username ");
            }
            if (userRepo.findByEmail(addStudentDTO.getEmail()) != null) {
                message.append("Email ");
            }
            if (!StringUtils.isBlank(message)) {
                message.append("is existed");
                rs.setMessage(message.toString());
                rs.setState(false);
                return rs;
            }
            try {
                if (userRepo.findByUsername(addStudentDTO.getUsername()) == null) {
                    if (userRepo.findByEmail(addStudentDTO.getEmail()) == null) {
                        if (userRepo.findByPhone(addStudentDTO.getPhone()) == null) {
                            user.setUsername(addStudentDTO.getUsername());
                            user.setFullname(addStudentDTO.getFullname());
                            user.setPassword(passwordEncoder.encode(addStudentDTO.getPassword()));
                            user.setEmail(addStudentDTO.getEmail());
                            user.setPhone(addStudentDTO.getPhone());
                            user.setAddress(addStudentDTO.getAddress());
                            user.setActive(true);
                            user.setEnabled(true);
                            userRepo.save(user);

                            student.setUserId(userRepo.findTopByOrderByIdDesc().getId());
                            student.setRoleId(1L);
                            student.setCode(addStudentDTO.getCode());
                            studentRepo.save(student);
                            rs.setMessage("Add successful");
                            rs.setState(true);
                        }
                    }
                } else {
                    rs.setMessage("Add failed");
                    rs.setState(false);
                }
            } catch (Exception ex) {
                rs.setMessage("Add failed");
                rs.setState(false);
            }
        }
        return rs;
    }

    @Override
    public ResponseStatus editStudent(AddStudentDTO addStudentDTO) {
        User user = new User();
        Student student = new Student();
        ResponseStatus rs = new ResponseStatus();
        StringBuilder message = new StringBuilder();

        if (addStudentDTO != null) {
            if (userRepo.findByUsername(addStudentDTO.getUsername()) != null) {
                message.append("Username ");
            }
            if (userRepo.findByEmail(addStudentDTO.getEmail()) != null) {
                message.append("Email ");
            }
            if (!StringUtils.isBlank(message)) {
                message.append("is existed");
                rs.setMessage(message.toString());
                rs.setState(false);
                return rs;
            }

            try {
                if (userRepo.findByUsername(addStudentDTO.getUsername()) == null) {
                    if (userRepo.findByEmail(addStudentDTO.getEmail()) == null) {
                        student.setUserId(addStudentDTO.getId());
                        student.setRoleId(4L);
                        studentRepo.save(student);

                        user.setId(addStudentDTO.getId());
                        user.setUsername(addStudentDTO.getUsername());
                        user.setFullname(addStudentDTO.getFullname());
                        user.setPassword(userRepo.findById(addStudentDTO.getId()).get().getPassword());
                        user.setEmail(addStudentDTO.getEmail());
                        user.setPhone(addStudentDTO.getPhone());
                        user.setAddress(addStudentDTO.getAddress());
                        user.setActive(addStudentDTO.isActive());
                        userRepo.save(user);
                        rs.setMessage("Update successful");
                        rs.setState(true);
                    }
                } else {
                    rs.setMessage("Update failed");
                    rs.setState(false);
                }
            } catch (Exception ex) {
                rs.setMessage("Update failed");
                rs.setState(false);
            }
        }
        return rs;
    }

    @Override
    public StudentDTO getProfileStudent(StudentDTO studentDTO) {
        if (studentDTO.getUser_name() != null) {
            try {
                return studentCustomRepo.getStudent(studentDTO);
            } catch (Exception ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public ResponseStatus editStudentByStudent(StudentDTO studentDTO) {
        User user = new User();
        Student student = new Student();
        ResponseStatus rs = new ResponseStatus();
        StringBuilder message = new StringBuilder();

        try {
            if (studentDTO != null) {
                if (userRepo.findById(studentDTO.getUser_Id()).get().getUsername().equals(studentDTO.getUser_name())) {
                    user.setId(studentDTO.getUser_Id());
                    String username = userRepo.findById(studentDTO.getUser_Id()).get().getUsername();
                    user.setUsername(username);
                    user.setFullname(studentDTO.getFull_name());
                    user.setPassword(userRepo.findById(studentDTO.getUser_Id()).get().getPassword());
                    user.setEmail(userRepo.findById(studentDTO.getUser_Id()).get().getEmail());
                    user.setPhone(studentDTO.getPhone());
                    user.setAddress(studentDTO.getAddress());
                    user.setActive(true);
                    user.setEnabled(true);
                    userRepo.save(user);
                    student.setId(studentRepo.findByUserId(studentDTO.getUser_Id()).getId());
                    student.setCode(studentRepo.findByUserId(studentDTO.getUser_Id()).getCode());
                    student.setUserId(studentDTO.getUser_Id());
                    student.setRoleId(2L);
                    studentRepo.save(student);
                    rs.setMessage("Ok");
                    rs.setState(true);
                    return rs;
                }

                if (userRepo.findByUsername(studentDTO.getUser_name()) == null) {
                    user.setId(studentDTO.getUser_Id());
                    user.setUsername(studentDTO.getUser_name());
                    user.setFullname(studentDTO.getFull_name());
                    user.setPassword(userRepo.findById(studentDTO.getUser_Id()).get().getPassword());
                    user.setEmail(userRepo.findById(studentDTO.getUser_Id()).get().getEmail());
                    user.setPhone(studentDTO.getPhone());
                    user.setAddress(studentDTO.getAddress());
                    user.setActive(true);
                    user.setEnabled(true);
                    userRepo.save(user);
                    student.setId(studentRepo.findByUserId(studentDTO.getUser_Id()).getId());
                    student.setUserId(studentDTO.getUser_Id());
                    student.setRoleId(3L);
                    studentRepo.save(student);
                    rs.setMessage("Update successful");
                    rs.setState(true);
                    return rs;
                }

                if (userRepo.findByUsername(studentDTO.getUser_name()) != null) {
                    message.append("Username ");
                }
                if (!StringUtils.isBlank(message)) {
                    message.append("is existed");
                    rs.setMessage(message.toString());
                    rs.setState(false);
                    return rs;
                }
            }
        } catch (Exception ex) {

        }
        return null;
    }

    @Override
    public ResponseStatus addEvent(EventDTO eventDTO) {
        ResponseStatus responseStatus = new ResponseStatus();
        Event event = new Event();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate1 = LocalDate.parse(eventDTO.getFrom_date(), formatter);
        LocalDate localDate2 = LocalDate.parse(eventDTO.getTo_date(), formatter);
        try {
            event.setName(eventDTO.getName());
            event.setImage(eventDTO.getImage());
            event.setFrom_date(localDate1);
            event.setTo_date(localDate2);
            eventRepo.save(event);
            responseStatus.setMessage("Successfully");
            responseStatus.setState(true);
            return responseStatus;
        } catch (Exception exception) {

        }
        responseStatus.setMessage("Failure");
        responseStatus.setState(false);
        return responseStatus;
    }

    @Override
    public ResponseStatus editEvent(EventDTO eventDTO) {
        ResponseStatus responseStatus = new ResponseStatus();
        Event event = new Event();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate1 = LocalDate.parse(eventDTO.getFrom_date(), formatter);
        LocalDate localDate2 = LocalDate.parse(eventDTO.getTo_date(), formatter);
        try {
            event.setId(eventDTO.getId());
            event.setName(eventDTO.getName());
            event.setImage(eventDTO.getImage());
            event.setFrom_date(localDate1);
            event.setTo_date(localDate2);
            eventRepo.save(event);
            responseStatus.setMessage("Successfully");
            responseStatus.setState(true);
            return responseStatus;
        } catch (Exception exception) {

        }
        responseStatus.setMessage("Failure");
        responseStatus.setState(false);
        return responseStatus;
    }

    @Override
    public ResponseStatus addViolate(ViolateDTO violateDTO) {
        ResponseStatus responseStatus = new ResponseStatus();

        if (violateDTO.getViolated_code() == null || studentRepo.findByCode(violateDTO.getViolated_code()) == null) {
            responseStatus.setMessage("Failure");
            responseStatus.setState(false);
            return responseStatus;
        }

        Violate violate = new Violate();
        try {
            User user = userRepo.findByUsername(violateDTO.getPost_user_name());
            Student student1 = studentRepo.findByCode(violateDTO.getViolated_code());
            Student student2 = studentRepo.findByUserId(user.getId());
            violate.setName(violateDTO.getName());
            violate.setImage(violateDTO.getImage());
            violate.setStudentViolatedId(student1.getId());
            violate.setStudentPostId(student2.getId());
            violateRepo.save(violate);
            if (String.valueOf(student1.getPoint()) == null) {
                student1.setPoint(0.0F);
            } else {
                student1.setPoint(student1.getPoint() - 3);
            }
            studentRepo.save(student1);
            if (String.valueOf(student2.getPoint()) == null) {
                student2.setPoint(0.0F);
            } else {
                student2.setPoint(student1.getPoint() + 3);
            }
            studentRepo.save(student2);
            responseStatus.setMessage("Successfully");
            responseStatus.setState(true);
            return responseStatus;
        } catch (Exception exception) {

        }
        responseStatus.setMessage("Failure");
        responseStatus.setState(false);
        return responseStatus;
    }

    @Override
    public ResponseStatus deactiveStudent(AddStudentDTO addStudentDTO) {
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            User user = userRepo.findByUsername(addStudentDTO.getUsername());
            userRepo.deactive(user.getId());
            responseStatus.setMessage("Successfully");
            responseStatus.setState(true);
            return responseStatus;
        } catch (Exception ex) {

        }
        responseStatus.setMessage("Failure");
        responseStatus.setState(false);
        return responseStatus;
    }

    @Override
    public SearchResultDTO<EventDTO> getEvent(EventDTO eventDTO) {
        List<EventDTO> dataResult;
        SearchResultDTO<EventDTO> searchResult = new SearchResultDTO<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Integer totalRecord = eventCustomRepo.getTotal(eventDTO).size();
            dataResult = eventCustomRepo.doSearch(eventDTO);

            if (dataResult != null && !dataResult.isEmpty()) {
                searchResult.setCode("0");
                searchResult.setSuccess(true);
                searchResult.setTitle("Success");
                searchResult.setMessage("Success");
                for (EventDTO i : dataResult) {
                    String sDate = i.getFrom_date();
                    Date sParseDate = sdf.parse(sDate);
                    String eDate = i.getTo_date();
                    Date eParseDate = sdf.parse(eDate);
                    i.setFrom_date(sdf1.format(sParseDate));
                    i.setTo_date(sdf1.format(eParseDate));
                }
                searchResult.setResultData(dataResult);
                searchResult.setTotalRecordNoLimit(totalRecord);
            } else {
                searchResult.setCode("0");
                searchResult.setSuccess(false);
                searchResult.setTitle("Failure");
                searchResult.setMessage("Failure");
                searchResult.setResultData(Collections.emptyList());
                searchResult.setTotalRecordNoLimit(0);
            }
            return searchResult;
        } catch (Exception e) {
            searchResult.setCode("0");
            searchResult.setSuccess(false);
            searchResult.setTitle("Failure");
            searchResult.setMessage("Failure");
            searchResult.setResultData(Collections.emptyList());
            searchResult.setTotalRecordNoLimit(0);
            return searchResult;
        }
    }

    @Override
    public List<Event> getEvent() {
        try {
            List<Event> list = eventRepo.findAll();
            return list;
        } catch (Exception ex) {

        }
        return null;
    }

    @Override
    public ResponseStatus addStudentInEvent(StudentInEventDTO studentInEventDTO) {
        ResponseStatus responseStatus = new ResponseStatus();
        try {

            if (studentInEventDTO.getUsername() == null) {
                responseStatus.setMessage("Failure");
                responseStatus.setState(false);
                return responseStatus;
            }

            User user = userRepo.findByUsername(studentInEventDTO.getUsername());
            Student student = studentRepo.findByUserId(user.getId());
            StudentInEvent sie = studentInEventRepo.findByStudentIdAndEventId(student.getId(), studentInEventDTO.getEventId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate lt = LocalDate.now();
//            formatter.format(lt);

            if (sie == null) {
                sie = new StudentInEvent();
                sie.setStudentId(student.getId());
                sie.setEventId(studentInEventDTO.getEventId());
                sie.setRegisterDate(lt);
                sie.setCaption(studentInEventDTO.getCaption());
                sie.setImage(studentInEventDTO.getImage());
                sie.setState(false);
                studentInEventRepo.save(sie);
                responseStatus.setMessage("Successfully");
                responseStatus.setState(true);
                return responseStatus;
            } else {
                if (!sie.isState()) {
                    sie.setCaption(studentInEventDTO.getCaption());
                    sie.setImage(studentInEventDTO.getImage());
                    sie.setState(true);
                    studentInEventRepo.save(sie);
                    responseStatus.setMessage("Successfully");
                    responseStatus.setState(true);
                    return responseStatus;
                } else {
                    responseStatus.setMessage("Failure");
                    responseStatus.setState(false);
                    return responseStatus;
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        responseStatus.setMessage("Failure");
        responseStatus.setState(false);
        return responseStatus;
    }

    @Override
    public SearchResultDTO<ViolateDTO> getViolate(ViolateDTO violateDTO) {
        List<ViolateDTO> dataResult;
        SearchResultDTO<ViolateDTO> searchResult = new SearchResultDTO<>();

        try {
            Integer totalRecord = violateCustomRepo.getTotal(violateDTO).size();
            dataResult = violateCustomRepo.doSearch(violateDTO);

            if (dataResult != null && !dataResult.isEmpty()) {
                searchResult.setCode("0");
                searchResult.setSuccess(true);
                searchResult.setTitle("Success");
                searchResult.setMessage("Success");
                searchResult.setResultData(dataResult);
                searchResult.setTotalRecordNoLimit(totalRecord);
            } else {
                searchResult.setCode("0");
                searchResult.setSuccess(false);
                searchResult.setTitle("Failure");
                searchResult.setMessage("Failure");
                searchResult.setResultData(Collections.emptyList());
                searchResult.setTotalRecordNoLimit(0);
            }
            return searchResult;
        } catch (Exception e) {
            searchResult.setCode("0");
            searchResult.setSuccess(false);
            searchResult.setTitle("Failure");
            searchResult.setMessage("Failure");
            searchResult.setResultData(Collections.emptyList());
            searchResult.setTotalRecordNoLimit(0);
            return searchResult;
        }
    }

    @Override
    public SearchResultDTO<DetailEventDTO> getEventDetail(DetailEventDTO detailEventDTO) {
        List<DetailEventDTO> dataResult;
        SearchResultDTO<DetailEventDTO> searchResult = new SearchResultDTO<>();

        try {
            Integer totalRecord = detailEventCustomRepo.getTotal(detailEventDTO).size();
            dataResult = detailEventCustomRepo.doSearch(detailEventDTO);

            if (dataResult != null && !dataResult.isEmpty()) {
                searchResult.setCode("0");
                searchResult.setSuccess(true);
                searchResult.setTitle("Success");
                searchResult.setMessage("Success");
                searchResult.setResultData(dataResult);
                searchResult.setTotalRecordNoLimit(totalRecord);
            } else {
                searchResult.setCode("0");
                searchResult.setSuccess(false);
                searchResult.setTitle("Failure");
                searchResult.setMessage("Failure");
                searchResult.setResultData(Collections.emptyList());
                searchResult.setTotalRecordNoLimit(0);
            }
            return searchResult;
        } catch (Exception e) {
            searchResult.setCode("0");
            searchResult.setSuccess(false);
            searchResult.setTitle("Failure");
            searchResult.setMessage("Failure");
            searchResult.setResultData(Collections.emptyList());
            searchResult.setTotalRecordNoLimit(0);
            return searchResult;
        }
    }

    @Override
    public List<Violate> getSelfViolate(User user) {
        try {
            User user1 = userRepo.findUserByUsername(user.getUsername());
            Student stu = studentRepo.findByUserId(user1.getId());
            List<Violate> list = violateRepo.findByStudentViolatedId(stu.getId());
            return list;
        } catch (Exception ex) {

        }
        return null;
    }


}

