# 📚 Proje Planı: Student Management API

## 1. Amaç

## 1. Model kismini yaziniz
- **WeahterResponse**
- 
  private String id;
- 
  private String requestedCityName;
- 
  private String cityName;
- 
  private String country;
- 
  private Integer temperature;
- 
  private LocalDateTime localDateTime; // hava durumu saati
- 
  private LocalDateTime responseLocalTime; // ne zaman response edilmis


---


---
## 2. Repository yazisiniz

- public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {

- // Select*from entity
- 
  Optional<WeatherEntity> findFirstByRequestedCityNameOrderByLocalDateTimeDesc(String city);
  }


---


## 3. DTO
- StudentDTO
    - id
    - fullName
    - email

---

## 4. Repository
- StudentRepository ➔ JpaRepository<Student, Long>

---

## 5. Service
- StudentService
    - createStudent(StudentDTO dto)
    - getAllStudents()
    - deleteStudentById(Long id)

---

## 6. Controller
- StudentController
    - POST `/api/students`
    - GET `/api/students`
    - DELETE `/api/students/{id}`

---

## 7. Validation
- Email alanı boş olamaz ve geçerli formatta olmalı.
- İsimler boş olamaz.

---

## 8. Exception Handling
- StudentNotFoundException
- GlobalExceptionHandler

---

## 9. Swagger
- Swagger UI kurularak API testleri kolaylaştırılacak.

---

## 10. Docker
- PostgreSQL veritabanı için Docker container kullanılacak.
- Spring Boot app containerize edilecek.

---

## 11. Git İlerlemesi
- [x] Day 1: Entity ve Repository oluşturuldu.
- [ ] Day 2: Service ve DTO katmanları yazılacak.
- [ ] Day 3: Controller ve Swagger kurulacak.

---

# 📅 Günlük Plan
- Her gün bir katman tamamlanacak.
- GitHub'a her gün bir commit atılacak.