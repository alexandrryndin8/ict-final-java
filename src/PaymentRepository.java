import java.util.List;

public interface PaymentRepository {
    void create(Payment payment);
    Payment read(int id);
    List<Payment> readAll();
    void update(Payment payment);
    void delete(int id);
}
