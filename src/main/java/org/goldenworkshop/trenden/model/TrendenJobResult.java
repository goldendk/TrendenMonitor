package org.goldenworkshop.trenden.model;

public class TrendenJobResult {
    private int duration;
    private String message;
    private boolean success;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "TrendenJobResult{" +
                "duration=" + duration +
                ", message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
