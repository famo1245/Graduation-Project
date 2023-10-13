//package meundi.graduationproject.domain;
//
//import lombok.Getter;
//import org.hibernate.validator.constraints.Range;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//@Getter
//public class ChatRoom {
//
//    @Id
//    @Column(name = "room_id")
//    String id;
//
//    @NotBlank
//    private String title;
//
//    /* 인원 수 */
//    @Range(min = 2, max= 10)
//    private int num;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    @Column(name = "author")
//    private Member member;
//
//    /* 작성일자 */
//    private LocalDateTime dateTime;
//
//    @ManyToOne
//    @JoinColumn(name = "culture_id")
//    private Culture culture;
//
//    @OneToMany(mappedBy = "member_id")
//    private List<Member> members;
//
//    /**
//     * create logic
//     * @param roomId
//     * @param title
//     * @param participants
//     * @param culture
//     * @param meatTime
//     */
//    public void CreatChatRoom(String roomId, String title, int participants, Culture culture,
//                               LocalDateTime meatTime) {
//        this.id = roomId;
//        this.title = title;
//        this.num = participants;
//        this.culture = culture;
//        this.dateTime = meatTime;
//    }
//}
