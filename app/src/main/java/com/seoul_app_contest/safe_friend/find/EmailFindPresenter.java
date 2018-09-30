package com.seoul_app_contest.safe_friend.find;

import com.seoul_app_contest.safe_friend.UserModel;

public class EmailFindPresenter implements EmailFindContract.Presenter {

    EmailFindContract.View view;
    UserModel model;

    boolean isAuth = false;

    public EmailFindPresenter(EmailFindContract.View view) {
        this.view = view;
        model = new UserModel();
    }

    @Override
    public void requestAuthNum(String phoneNum) {
        if (model.checkPhoneNumType(phoneNum)) {
            view.showToast("요청중..");
            model.sendAuthNum(phoneNum, new UserModel.SendAuthNumCallbackListener() {
                @Override
                public void onSuccess() {
                    isAuth = false;
                    view.showToast("메시지가 전송되었습니다.");

                }

                @Override
                public void onFail(String e) {
                    view.showToast("메시지 전송에 실패했습니다. " + e);
                }
            });
        }else {

        }

    }

    @Override
    public void checkAuthNum(String authNum) {
        if (model.checkAuthNum(authNum)) {
            isAuth = true;
            view.showToast("확인되었습니다.");
        }else {
            view.showToast("잘못된 인증번호입니다.");
        }
    }

    @Override
    public void findEmail(String name, String phoneNum) {
        model.findUserEmail(name, phoneNum, new UserModel.FindUserEmailCallbackListener() {
            @Override
            public void onSuccess(String email) {
                view.showEmailDialog();
                view.setEmail(email);
                view.setConfirmEmail("아이디 찾기가 완료되었습니다.");
            }

            @Override
            public void onFail() {
                view.showEmailDialog();
                view.setEmail("");
                view.setConfirmEmail("아이디가 존재하지 않습니다.");
            }
        });
    }
}
