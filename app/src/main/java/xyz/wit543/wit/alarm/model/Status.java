package xyz.wit543.wit.alarm.model;

/**
 * Created by WIT on 04-Jun-16.
 */
public class Status {
    private Alarm alarm;
    private User sender;
    private User reciver;

    public Status(Alarm alarm, User sender, User reciver) {
        this.alarm = alarm;
        this.sender = sender;
        this.reciver = reciver;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public User getSender() {
        return sender;
    }

    public User getReciver() {
        return reciver;
    }
    public Momemto save(){
        return new Momemto();
    }
    public void load(Momemto momemto){
        momemto.restore();
    }
    public class Momemto{
        private Alarm alarm;
        private User sender;
        private User reciver;

        Momemto(){
            this.alarm=Status.this.alarm;
            this.sender=Status.this.sender;
            this.reciver=Status.this.reciver;
        }
        private void restore(){
            Status.this.alarm=this.alarm;
            Status.this.sender=this.sender;
            Status.this.reciver=this.reciver;
        }
    }
}
