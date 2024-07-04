package work.entity;
import lombok.Data;

@Data
public class ResutSet<T> {
    private String status;
    private String message;
    private T data;
    private String token;
}
