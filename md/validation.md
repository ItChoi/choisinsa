# validation

## Validation
- ValidationUtils.rejectIfEmpty() 

- 복잡한 검증 로직 분리하기.
  - 컨트롤러가 너무 많은 역할을 하면 안 된다.
  - 컨트롤러에 검증 로직이 너무 많은 경우 검증 클래스를 분리하여 DI 받아 사용해도 된다.

- 검증을 위한 클래스를 Validator 나누어도 된다.
  - implements Validator 
    - 스프링이 제공한다.
    - BingResults의 로직을 담을 수 있다.
    - 클래스를 스프링 빈으로 등록 후 validate 메소드 호출 -> target(item, BindingResults) 담아 호출
    - 구현하지 않고 매번 생성하여 사용해도 된다. 그러나 왜 쓸까? 이유가 있다.
      - 스프링 빈이 대신 호출하게 하여 코드를 없앨 수 있다.
      - Bean Validation을 위한 복선쓰

- 스프링이 Validator를 별도로 지원하는 이유는 체계적 검증 기능을 도입하기 위함이다.
  - WebDataBinder를 통해 사용하기
    - 아이템 객체 파라미터 바인드, 검증 -> 스프링 MVC가 내부에서 사용하는 기능 -> 밖으로 꺼내서 검증을 넣어줘야 검증들을 적용한다.
  ```java
  // 이 컨트롤에 있는 메소드 호출마다 이 검증을 적용하게 된다. 메소드마다 검증 대상은 @Validated 추가
  @InitBinder
  public void init(WebDataBinder dataBinder) {
    dataBinder.addValidators(memberValidator);
  }
  ```
  
- Application 클래스에 WebMvcConfigurer을 구현하여 getValidator을 오버라이딩 할 수 있는데, 이럴 경우 BeanValidator가 자동 등록되지 않으니 주의

- @Validated 스프링 전용 검증 애노테이션 
- @Valid -> javax -> 자바 표준 애노테이션
- 둘 중 아무거나 써도 되지만, 약간 기능의 차이는 있다. 검증을 하는 것은 똑같지만 파라미터 추가 등..

## 클라이언트 검증, 서버 검증
- 클라이언트 검증은 조작 가능하므로 보안에 취약하다.
- 서버만으로 검증하기엔 이용자에게 즉각적인 반응이 부족하여 불편하다.
- 결론적으로 둘 다 적절히 섞어가며 사용하고, 최종적으로 서버 검증은 필수


## Bean Validation
- 특정 필드에 대한 검증은 대부분의 체크 사항은 빈 값, 크기 등 일반적인 로직이다. 
  - 일반적인 검증을 애노테이션으로 해결 가능
  - @NotBlank, @Range, @NotNull, @Max, @Email, ...
    - 개발자가 번거롭게 검증 코드를 매번 작성하지 않아도 된다.
- 특정 구현체가 아니라 Bean VAlidation 2.0(JSR-380) 기술 표준이다.
  - 인터페이스만 제공하고 구현체는 오픈소스 어느 것을 사용해도 된다.
  - 일반적 사용 구현체는 하이버네이트 Validator (ORM이랑 전혀 관련이 없다.)
- 의존성 추가 필요: ... starter-validation
- 바인딩에 실패하면 필드는 검증을 적용하지 않는다. (int 값에 String을 넣는 경우)

### 스프링 MVC는 어떻게 Bean Validator를 사용할 수 있을까?
- validation 의존성 추가시 스프링 부트가 자동으로 Bean Validator를 인지하고 스프링에 통합한다.


