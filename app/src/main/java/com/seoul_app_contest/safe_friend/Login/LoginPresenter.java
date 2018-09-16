package com.seoul_app_contest.safe_friend.Login;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private LoginModel model;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        model = new LoginModel();
    }

    @Override
    public void setUserData(String email, String password) {
        model.setUserData(email, password);
    }

    @Override
    public void autoLogin() {
        if (model.existCurrentUser()){
            view.redirectMainActivity();
        }
    }


    @Override
    public void login() {
        model.requestFirebaseAuth(new LoginModel.AuthCallback() {
            @Override
            public void onSuccess(String email, String password) {

            }

            @Override
            public void onFail() {
                view.showErrorToast("Fail..");
            }
        });

    }
}
