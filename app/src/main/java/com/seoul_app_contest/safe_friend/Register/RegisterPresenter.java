package com.seoul_app_contest.safe_friend.Register;

public class RegisterPresenter implements RegisterContract.Presenter {
    RegisterContract.View view;
    RegisterModel model;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.model = new RegisterModel();
    }

    @Override
    public void setUserData(String email, String password, String name, String birthDay) {
        model.setData(email, password, name, birthDay);
    }

    @Override
    public void requestAuthNum(String phoneNum) {
        model.setPhoneNum(phoneNum);
        model.sendAuthNum(new RegisterModel.CallbackListener() {
            @Override
            public void onSuccess(String authNum) {
                model.setAuthNum(authNum);
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    public void checkAuthNum(String authNum) {
        if (model.isValidAuthNum(authNum)){

        }else{

        }
    }

    @Override
    public void addFirestore() {
        model.register(new RegisterModel.CallbackListener() {
            @Override
            public void onSuccess(String authNum) {

            }

            @Override
            public void onSuccess() {
                view.redirectMainActivity();
            }

            @Override
            public void onFail() {

            }
        });
    }
}
