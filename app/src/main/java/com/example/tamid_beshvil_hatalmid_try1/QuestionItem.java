package com.example.tamid_beshvil_hatalmid_try1;

public class QuestionItem {
    private String subject;      // מתמטיקה / פיזיקה
    private String bookName;     // שם הספר
    private String examName;     // שם המבחן/הפרק
    private String solutionLink; // קישור לפתרון המלא
    private String videoLink;    // קישור לסרטון (null אם אין)
    private String examLink;     // קישור לשאלון בלבד

    public QuestionItem(String subject, String bookName, String examName, String solutionLink, String videoLink, String examLink) {
        this.subject = subject;
        this.bookName = bookName;
        this.examName = examName;
        this.solutionLink = solutionLink;
        this.videoLink = videoLink;
        this.examLink = examLink;
    }

    public String getSubject() { return subject; }
    public String getBookName() { return bookName; }
    public String getExamName() { return examName; }
    public String getSolutionLink() { return solutionLink; }
    public String getVideoLink() { return videoLink; }
    public String getExamLink() { return examLink; }
}