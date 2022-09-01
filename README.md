# Spring 주특기 심화 11조 팀과제

<aside>
🗒️ 요구사항에 맞춰 API 구성 및 Git을 활용하여 협업하기

</aside>

---

<aside>
💡 **과제 요구 사항**

</aside>

### 공통

- cascade를 활용해 연관관계 중 상위 객체가 삭제될 경우, 하위 객체도 모두 삭제되게 하기

### 기능0

- **게시글 1개 조회(상세 페이지용) 시, 댓글/대댓글/좋아요 수 모두 포함하여 response**

### 기능1

- **게시글 좋아요 기능 및 댓글/대댓글 좋아요**
    - **`200`** AccessToken이 있고, 유효한 Token일 때(== 로그인 상태일 때)만 좋아요 가능하게 하기
    - **`Exception`** AccessToken이 없거나, 유효하지 않은 Token일 때 ‘로그인이 필요합니다.’를 200 정상 응답으로 나타내기
    - 게시글 목록 response에 id, 제목, 작성자, 좋아요 개수, 대댓글 제외한 댓글 개수, 등록일, 수정일 나타내기
    - **API 종류**
        1.  좋아요 등록 API
            - AccessToken이 있고, 유효한 Token일 때만 요청 가능하도록 하기
            - 게시글, 댓글, 대댓글 reponse에 좋아요 개수 함께 나타내기
        2. 좋아요 취소 API
            - AccessToken이 있고, 유효한 Token일 때만 요청 가능하도록 하기
            - 게시글, 댓글, 대댓글 reponse에 좋아요 개수 함께 나타내기

### 기능2

- **대댓글**
    - **`200`** AccessToken이 있고, 유효한 Token일 때(== 로그인 상태일 때)만 댓글/대댓글 작성 가능하게 하기
    - 댓글 리스트 response할 때 대댓글 리스트도 모두 포함해서 보여주기
    - **`Exception`** AccessToken이 없거나, 유효하지 않은 Token일 때 ‘로그인이 필요합니다.’를 200 정상 응답으로 나타내기
    - **API 종류**
        1.  대댓글 목록 조회 API
            - AccessToken이 없어도 댓글 목록 조회가 가능하도록 하기
            - 조회하는 게시글에 작성된 모든 댓글을 response에 포함하기
        2. 대댓글 작성 API
            - AccessToken이 있고, 유효한 Token일 때만 댓글 작성이 가능하도록 하기
        3. 대댓글 수정 API
            - AccessToken이 있고, 유효한 Token이면서 해당 사용자가 작성한 댓글만 수정 가능하도록 하기
        4. 대댓글 삭제 API
            - AccessToken이 있고, 유효한 Token이면서 해당  사용자가 작성한 댓글만 삭제 가능하도록 하기

### 기능3

- (게시글에 들어갈) **이미지 업로드**
    - hint. AWS IAM, SDK, S3
    - 게시글 작성 중 요청하는 플로우이며, 게시글당 1개의 이미지만 업로드 가능하다는 전제로 진행
    - **`200`** AccessToken이 있고, 유효한 Token일 때(== 로그인 상태일 때)만 요청 가능하게 하기
    + s3 객체 주소를 response로 반환하기 (이미지 url)
    - `Exception` Multipartfile로 이미지 파일을 받고, 파일 변환에 실패할 경우, ‘파일 변환에 실패했습니다’를 200 정상 응답으로 나타내기
    - **API 종류**
        1.  이미지 조회 API
            - 게시글 조회 시 이미지 url 포함해서 response 하기
        2. 이미지 등록 API
            - AccessToken이 있고, 유효한 Token일 때만 이미지 업로드 API 요청이 가능하도록 하기

### 기능4

- **게시글**
    - 이미지 업로드 후, 받아온 response의 imgUrl을 포함하여 글 제목,내용,이미지 주소를 함께 업로드한다.
    - **`200`** AccessToken이 있고, 유효한 Token일 때(== 로그인 상태일 때)만 요청 가능하게 하기
    - **`Exception`** 해당 게시글 조회 시, 존재하지 않는 게시글 id 일 때, ‘존재하지 않는 게시글 입니다.’를 200 정상 응답으로 나타내기
    - API 종류
        1. 전체 게시글 목록 조회 API
        2. 게시글 하나 조회 API
        3. 게시글 작성 API
        4. 게시글 수정 API
        5. 게시글 삭제 API

### 기능5

- **마이페이지**
    - AccessToken 속 사용자가 작성한 게시글, 댓글/대댓글, 좋아요한 게시글/댓글 분류하여 response
- **게시글과 댓글 좋아요 개수 표시**
    - AccessToken이 없어도 좋아요 개수 조회가 가능하도록 하기
    - 조회하는 게시글 리스트에 작성된 모든 좋아요 개수를 게시글 리스트 response에 포함하기
    - 조회하는 각 게시글에 작성된 모든 좋아요 개수를 게시글 리스트 response에 포함하기
    - 조회하는 각 댓글/대댓글에 작성된 모든 좋아요 개수를 댓글 리스트 response에 포함하기

### 기능6

- **스케줄러**
    - 매일 AM 01:00 마다 댓글이 0개인 게시물 전체 삭제하기
    - 삭제될 때마다 `"게시물 <{게시물 이름}>이 삭제되었습니다."` 라는 info level log 남기기
        
        ![image](https://user-images.githubusercontent.com/62546335/187889428-4f003ca9-2735-4c8c-8b3d-3dd9b3851d1e.png)
        
    - cf. 스케줄러 example
        - 요구 기능
            
            <aside>
            👉 매일 새벽 1시에 관심 상품 목록 제목으로 검색해서, 최저가 정보를 업데이트하는 스케줄러를 만들어보겠습니다.
            
            </aside>
            
        - Scheduler 만들기
            - **ExampleApplication 클래스**
                
                ```jsx
                @EnableScheduling // 스프링 부트에서 스케줄러가 작동하게 합니다.
                @EnableJpaAuditing // 시간 자동 변경이 가능하도록 합니다.
                @SpringBootApplication // 스프링 부트임을 선언합니다.
                public class ExampleApplication {
                
                    public static void main(String[] args) {
                        SpringApplication.run(ExampleApplication.class, args);
                    }
                
                }
                ```
                
            - **Scheduler 클래스**
                
                ```jsx
                @RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
                @Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
                public class Scheduler {
                
                    private final ProductRepository productRepository;
                    private final ProductService productService;
                    private final NaverShopSearch naverShopSearch;
                
                    // 초, 분, 시, 일, 월, 주 순서
                    @Scheduled(cron = "0 0 1 * * *")
                    public void updatePrice() throws InterruptedException {
                        System.out.println("가격 업데이트 실행");
                        // 저장된 모든 관심상품을 조회합니다.
                        List<Product> productList = productRepository.findAll();
                        for (int i=0; i<productList.size(); i++) {
                            // 1초에 한 상품 씩 조회합니다 (Naver 제한)
                            TimeUnit.SECONDS.sleep(1);
                            // i 번째 관심 상품을 꺼냅니다.
                            Product p = productList.get(i);
                            // i 번째 관심 상품의 제목으로 검색을 실행합니다.
                            String title = p.getTitle();
                            String resultString = naverShopSearch.search(title);
                            // i 번째 관심 상품의 검색 결과 목록 중에서 첫 번째 결과를 꺼냅니다.
                            List<ItemDto> itemDtoList = naverShopSearch.fromJSONtoItems(resultString);
                            ItemDto itemDto = itemDtoList.get(0);
                            // i 번째 관심 상품 정보를 업데이트합니다.
                            Long id = p.getId();
                            productService.updateBySearch(id, itemDto);
                        }
                    }
                }
                ```
                
            - **ProductService 클래스**
                
                ```jsx
                @RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
                @Service // 서비스임을 선언합니다.
                public class ProductService {
                
                    private final ProductRepository productRepository;
                
                    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
                    public Long update(Long id, ProductMypriceRequestDto requestDto) {
                        Product product = productRepository.findById(id).orElseThrow(
                                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
                        );
                        product.update(requestDto);
                        return id;
                    }
                
                    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
                    public Long updateBySearch(Long id, ItemDto itemDto) {
                        Product product = productRepository.findById(id).orElseThrow(
                                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
                        );
                        product.updateByItemDto(itemDto);
                        return id;
                    }
                }
                ```
                
            - **Product 클래스**
                
                ```java
                @Getter // get 함수를 일괄적으로 만들어줍니다.
                @NoArgsConstructor // 기본 생성자를 만들어줍니다.
                @Entity // DB 테이블 역할을 합니다.
                public class Product extends Timestamped{
                
                    // ID가 자동으로 생성 및 증가합니다.
                    @GeneratedValue(strategy = GenerationType.AUTO)
                    @Id
                    private Long id;
                
                    // 반드시 값을 가지도록 합니다.
                    @Column(nullable = false)
                    private String title;
                
                    @Column(nullable = false)
                    private String image;
                
                    @Column(nullable = false)
                    private String link;
                
                    @Column(nullable = false)
                    private int lprice;
                
                    @Column(nullable = false)
                    private int myprice;
                
                    // 관심 상품 생성 시 이용합니다.
                    public Product(ProductRequestDto requestDto) {
                        this.title = requestDto.getTitle();
                        this.image = requestDto.getImage();
                        this.link = requestDto.getLink();
                        this.lprice = requestDto.getLprice();
                        this.myprice = 0;
                    }
                
                    public void updateByItemDto(ItemDto itemDto) {
                        this.lprice = itemDto.getLprice();
                    }
                
                    // 관심 가격 변경 시 이용합니다.
                    public void update(ProductMypriceRequestDto requestDto) {
                        this.myprice = requestDto.getMyprice();
                    }
                }
                ```
                
        - 확인
         - 임의의 시간을 넣어 작동함을 확인합니다.
