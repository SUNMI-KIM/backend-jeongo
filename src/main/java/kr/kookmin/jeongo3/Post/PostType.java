package kr.kookmin.jeongo3.Post;

public enum PostType {

    QNA("지식인"), FREE("자유"), ZEP("제페토");

    private String postName;

    PostType(String postName) {
        this.postName = postName;
    }
}
