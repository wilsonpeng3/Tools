/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package rocky.proxy;

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
