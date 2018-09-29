package com.seoul_app_contest.safe_friend.login;

import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.UserModel;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private UserModel model;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        model = new UserModel();

    }


    @Override
    public void signIn(final String email, final String password) {
        if (email.length() != 0 && password.length() != 0) {
            if (model.getUserType() == 0) { // 유저일때
                model.isUser(email, new UserModel.IsUserCallbackListener() {
                    @Override
                    public void exist() {
                        model.signIn(email, password, new UserModel.SignInCallbackListener() {
                            @Override
                            public void onSuccess() {
                                view.redirectMainActivity();
                            }

                            @Override
                            public void onFail(String e) {
                                view.showErrorToast("잘못된 정보입니다." + e);
                            }
                        });
                    }

                    @Override
                    public void notExist() {
                        view.showErrorToast("존재하지 않는 이메일입니다.");
                    }
                });

            }else { // 지킴이일때
                model.isProtector(email, new UserModel.IsProtectorCallbackListener() {
                    @Override
                    public void exist() {
                        model.protectorSignIn(email, password, new UserModel.SignInCallbackListener() {
                            @Override
                            public void onSuccess() {
                                view.redirectProtectorMainActivity();
                            }

                            @Override
                            public void onFail(String e) {
                                view.showErrorToast("잘못된 정보입니다." + e);
                            }
                        });
                    }

                    @Override
                    public void notExist() {
                        view.showErrorToast("존재하지 않는 이메일입니다.");
                    }
                });

            }

        }else {
            view.showErrorToast("모든 정보를 입력해주세요.");
        }

    }

    @Override
    public void changeLoginMode(int id) {
        if (id == R.id.login_user_btn){
            view.selectUserLogin();
            model.setUserType(0);
        }else {
            model.setUserType(1);
            view.selectProtectorLogin();
        }
    }
}
