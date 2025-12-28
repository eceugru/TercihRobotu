package com.nyym.TercihRobotu.Controller;

import com.nyym.TercihRobotu.DAO.AppDAO;
import com.nyym.TercihRobotu.Entity.Factory.EntityFactory;
import com.nyym.TercihRobotu.Entity.StudentInfo;
import com.nyym.TercihRobotu.Entity.User;
import com.nyym.TercihRobotu.Entity.departments;
import com.nyym.TercihRobotu.Entity.preferences;
import com.nyym.TercihRobotu.Service.RecommendationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/students")
public class StudentController {

    private AppDAO appDAO;

    private RecommendationService  recommendationService;

    public StudentController(AppDAO theAppDAO, RecommendationService theRecommendationService) {
        this.appDAO = theAppDAO;
        this.recommendationService = theRecommendationService;
    }

    @GetMapping("/showStudentForm")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "SignIn";
    }

    @PostMapping("/giris")
    public String login(
            @RequestParam("email") String theEmail,
            @RequestParam("password") String thePassword,
            Model model,
            HttpSession session
    )
    {
        Object entity = EntityFactory.createEntity("user");
        User user = (User) entity;


        user = appDAO.findUserByEmail(theEmail);
        session.setAttribute("user", user);
        model.addAttribute("user", user);
        System.out.println("DEBUG - User added to session: " + user.getEmail());

        StudentInfo studentId = appDAO.findStudentInfoByUser(user);

        System.out.println("DEBUG - Student ID: " + studentId.getId());

        List<departments> departments = recommendationService.getRecommendationsForStudent(studentId.getId());
        System.out.println("DEBUG - Recommendations count: " + departments.size());

        model.addAttribute("departments", departments);



        return "redirect:/students/MainPage";
    }

    @GetMapping("/showFormStudentForAdd")
    public String showFormStudentForAdd(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "RegisterPage";

    }

    @PostMapping("/save")
    public String save(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model,
            HttpSession session
    ) {
        // Factory Method tasarım kalıbı
        Object entity = EntityFactory.createEntity("user");
        User user = (User) entity;

        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(true);
        appDAO.save(user);

        session.setAttribute("user", user);
        // model.addAttribute("user", user);
        return "redirect:/students/showStudentForm";

    }

    @GetMapping("/MainPage")
    public String showMainPage(HttpServletRequest request, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }
        StudentInfo student = appDAO.findStudentInfoByUser(user);
        List<departments> departments = recommendationService.getRecommendationsForStudent(student.getId());

        model.addAttribute("departments", departments);
        model.addAttribute("user", user);


        // CSRF token'ı ekleyin
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }

        model.addAttribute("user", user);
        return "MainPage";
    }

    @GetMapping("/PreferenceSettings")
    public String showPreferenceSettings() {
        return "/PreferenceSettings";
    }



    @PostMapping("/studentLoading")
    public String studentLoading(

            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("examScore")double  examScore,
            @RequestParam("scoreType")String scoreType,
            @RequestParam("ranking") double ranking,
            @RequestParam("interestAreas") String interestAreas,
            @RequestParam("gender") String gender,
            HttpSession session
    ) {

        User user = (User) session.getAttribute("user");

        // Factory Method tasarım kalıbı
        Object studentObj = EntityFactory.createEntity("student");
        StudentInfo student = (StudentInfo) studentObj;

        student  = appDAO.findStudentInfoByUser(user);

        if (student == null) {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setUser(user);
            studentInfo.setFirstName(firstName);
            studentInfo.setLastName(lastName);
            studentInfo.setExamScore(examScore);
            studentInfo.setScoreType(scoreType);
            studentInfo.setRanking(ranking);
            studentInfo.setInterestAreas(interestAreas);
            studentInfo.setGender(gender);
            appDAO.save(studentInfo);
        }else{
            // Var olan kaydı güncelle

            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setExamScore(examScore);
            student.setScoreType(scoreType);
            student.setRanking(ranking);
            student.setInterestAreas(interestAreas);
            student.setGender(gender);
            appDAO.update(student);

        }
        session.setAttribute("user", user);
        return "redirect:/students/MainPage";
    }

    @GetMapping("/PreferenceBasket")
    public String showPreferenceBasket(Model model, HttpSession session) {
        // Oturum kontrolü
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "showStudentForm"; // Giriş yapılmamışsa login sayfasına yönlendir
        }

        // Öğrenci bilgilerini al
        StudentInfo student = appDAO.findStudentInfoByUser(user);
        if (student == null) {
            return "studentInfoForm"; // Öğrenci bilgisi yoksa form sayfasına yönlendir
        }

        // Tercihleri getir
        List<preferences> preferences = appDAO.findPreferencesByStudentOrderByOrder(student);
        model.addAttribute("tercihListesi", preferences);

        return "PreferenceBasket";
    }


    @PostMapping("/addToBasket")
    public String addToBasket(
            @RequestParam int yopCode,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        // 1. Kullanıcı kontrolü
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/students/showStudentForm";
        }

        // 2. Öğrenci bilgisini al
        StudentInfo student = appDAO.findStudentInfoByUser(user);
        if (student == null) {
            redirectAttributes.addFlashAttribute("error", "Öğrenci bilgileriniz eksik!");
            return "redirect:/students/PreferenceSettings";
        }

        // 3. Programı bul
        departments program = appDAO.findDepartmentByYopCode(yopCode);
        if (program == null) {
            redirectAttributes.addFlashAttribute("error", "Program bulunamadı!");
            return "redirect:/students/MainPage";
        }

        // 4. Tercih oluştur ve kaydet
        preferences preference = new preferences();
        preference.setStudent(student);
        preference.setDepartment(program);
        preference.setPreference_order(appDAO.findMaxPreferenceOrder(student) + 1);

        appDAO.save(preference);

        // 5. Başarı mesajı ve aynı sayfada kal
        redirectAttributes.addFlashAttribute("success", program.getProgram_name() + " sepete eklendi!");

        // MainPage için gerekli verileri tekrar yükle
        List<departments> departments = recommendationService.getRecommendationsForStudent(student.getId());
        model.addAttribute("departments", departments);
        model.addAttribute("user", user);

        return "redirect:/students/MainPage";
    }

    @PostMapping("/removeFromBasket")
    public String removeFromBasket(
            @RequestParam int yopCode,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // 1. Kullanıcı kontrolü
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/students/showStudentForm";
        }

        // 2. Öğrenci bilgisini al
        StudentInfo student = appDAO.findStudentInfoByUser(user);
        if (student == null) {
            redirectAttributes.addFlashAttribute("error", "Öğrenci bilgileriniz eksik!");
            return "redirect:/students/PreferenceSettings";
        }

        // 3. Silinecek tercihi bul
        departments program = appDAO.findDepartmentByYopCode(yopCode);
        if (program == null) {
            redirectAttributes.addFlashAttribute("error", "Program bulunamadı!");
            return "redirect:/students/PreferenceBasket";
        }

        // 4. Tercihi sil
        boolean silmeSonucu = appDAO.deletePreference(student, program);

        if (silmeSonucu) {
            redirectAttributes.addFlashAttribute("success", program.getProgram_name() + " sepetten çıkarıldı!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Silme işlemi başarısız oldu!");
        }

        return "redirect:/students/PreferenceBasket";
    }

    @PostMapping("/savePreferencesBasket")
    @ResponseBody
    public String savePreferencesBasket(@RequestBody List<Map<String, Object>> orderList,
                                        HttpSession session) {

        User user = (User) session.getAttribute("user");
        StudentInfo student = appDAO.findStudentInfoByUser(user);

        if (student == null) {
            return "Öğrenci bilgisi bulunamadı!";
        }

        try {
            for (int i = 0; i < orderList.size(); i++) {
                Map<String, Object> item = orderList.get(i);
                int yopCode = (int) item.get("yopKodu");
                int newOrder = (int) item.get("sira");

                departments department = appDAO.findDepartmentByYopCode(yopCode);
                if (department != null) {
                    preferences preference = appDAO.findPreferenceByStudentAndDepartment(student, department);
                    if (preference != null) {
                        preference.setPreference_order(newOrder);
                        appDAO.update(preference);
                    }
                }
            }
            return "Tercih sıralamanız başarıyla kaydedildi!";
        } catch (Exception e) {
            return "Sıralama kaydedilirken hata oluştu: " + e.getMessage();
        }
    }






}
