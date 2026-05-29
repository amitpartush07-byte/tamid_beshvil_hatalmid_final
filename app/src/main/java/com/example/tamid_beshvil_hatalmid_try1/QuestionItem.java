package com.example.tamid_beshvil_hatalmid_try1;

import com.google.firebase.database.Exclude;

public class QuestionItem {
    private String subject;
    private String desc;
    private String examName;
    private String solutionLink;
    private String videoLink;
    private String examLink;
    private String ownerId;
    private String key; // To store the Firebase unique ID

    public QuestionItem() {}

    public QuestionItem(String subject, String desc, String examName, String solutionLink, String videoLink, String examLink, String ownerId) {
        this.subject = subject;
        this.desc = desc;
        this.examName = examName;
        this.solutionLink = solutionLink;
        this.videoLink = videoLink;
        this.examLink = examLink;
        this.ownerId = ownerId;
    }

    public String getSubject() { return subject; }
    public String getDesc() { return desc; }
    public String getExamName() { return examName; }
    public String getSolutionLink() { return solutionLink; }
    public String getVideoLink() { return videoLink; }
    public String getExamLink() { return examLink; }
    public String getOwnerId() { return ownerId; }

    @Exclude 
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
}
