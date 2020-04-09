package com.yuanye.gwes.bean;

/**
 * Register Info State
 */
public class RIS {
    private boolean bUser;
    private boolean bPswd;
    private boolean bPswd2;
    private boolean bPhone;

    public RIS() {
    }

    public RIS(boolean bUser, boolean bPswd, boolean bPswd2, boolean bPhone) {
        this.bUser = bUser;
        this.bPswd = bPswd;
        this.bPswd2 = bPswd2;
        this.bPhone = bPhone;
    }

    public boolean isbUser() {
        return bUser;
    }

    public void setbUser(boolean bUser) {
        this.bUser = bUser;
    }

    public boolean isbPswd() {
        return bPswd;
    }

    public void setbPswd(boolean bPswd) {
        this.bPswd = bPswd;
    }

    public boolean isbPswd2() {
        return bPswd2;
    }

    public void setbPswd2(boolean bPswd2) {
        this.bPswd2 = bPswd2;
    }

    public boolean isbPhone() {
        return bPhone;
    }

    public void setbPhone(boolean bPhone) {
        this.bPhone = bPhone;
    }

    public boolean getState(){
        return bUser & bPswd & bPswd2 & bPhone;
    }
}
