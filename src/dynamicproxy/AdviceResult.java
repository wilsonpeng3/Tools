package dynamicproxy;

public class AdviceResult {
    private Object realResult;

    public AdviceResult(Object realResult) {
        this.realResult = realResult;
    }

    public Object getRealResult() {
        return realResult;
    }

    public void setRealResult(Object realResult) {
        this.realResult = realResult;
    }
}
