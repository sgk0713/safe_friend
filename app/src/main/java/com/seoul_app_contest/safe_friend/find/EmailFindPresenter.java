package com.seoul_app_contest.safe_friend.find;

import com.seoul_app_contest.safe_friend.UserModel;

public class EmailFindPresenter implements EmailFindContract.Presenter {

    EmailFindContract.View view;
    UserModel model;

    public EmailFindPresenter(EmailFindContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void requestAuthNum(String phoneNum) {
        if (model.checkPhoneNumType(phoneNum)) {
            model.sendAuthNum(phoneNum, new UserModel.SendAuthNumCallbackListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFail(String e) {

                }
            });
        }else {

        }

    }

    @Override
    public void checkAuthNum(String authNum) {
        if (model.checkAuthNum(authNum)) {

        }else {

        }
    }

    @Override
    public void findEmail(String name, String phoneNum) {
        model.findUserEmail(name, phoneNum, new UserModel.FindUserEmailCallbackListener() {
            @Override
            public void onSuccess(String email) {
                view.showEmailDialog();
                view.setEmail(email);
            }

            @Override
            public void onFail() {

            }
        });
    }
}
