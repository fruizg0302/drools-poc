package com.neo.drools.model.fact;

public class AddressCheckResult {

    private boolean postCodeResult = false; // true verification succesful；false verification failed failed

    public boolean isPostCodeResult() {
        return postCodeResult;
    }

    public void setPostCodeResult(boolean postCodeResult) {
        this.postCodeResult = postCodeResult;
    }
}
