package dgtic.core.siac.system.exception;

public class InsufficientStockException extends RuntimeException {

    //Movimientos en inventario
    public InsufficientStockException(String message) {
        super(message);
    }
}