package com.seoul_app_contest.safe_friend.register;

import com.seoul_app_contest.safe_friend.R;
import com.seoul_app_contest.safe_friend.UserModel;

public class RegisterPresenter implements RegisterContract.Presenter {
    RegisterContract.View view;
    UserModel model;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.model = new UserModel();
    }


    @Override
    public void setUserData(String email, String password, String name, String gender, String birthDay, String address, String phoneNum) {

    }

    @Override
    public void checkValidEmail(final String email) {
        if (model.checkEmailType(email)) {
            model.checkEmail(email, new UserModel.CheckEmailCallbackListener() {
                @Override
                public void exist() {
                    model.setCheckEmail(false);
                    view.showToast("존재하는 이메일 입니다.");
                }

                @Override
                public void notExist() {
                    model.setCheckEmail(true);
                    view.showToast("사용할 수 있는 이메일 입니다.");
                    model.setEmail(email);
                }
            });
        } else {
            view.showToast("이메일 형식을 올바르게 적어주세요.");
        }
    }

    @Override
    public void requestAuthNum(final String phoneNum) {
        if (model.checkPhoneNumType(phoneNum)) {
            model.sendAuthNum(phoneNum, new UserModel.SendAuthNumCallbackListener() {
                @Override
                public void onSuccess() {
                    view.showToast("메세지가 전송되었습니다.");
                    view.enableBtn(R.id.register_auth_num_btn);
                    model.setPhoneNum(phoneNum);
                }

                @Override
                public void onFail(String e) {
                    view.showToast(e);
                }
            });
        } else {
            view.showToast("올바른 전화번호를 입력해주세요.");
        }
    }

    @Override
    public void checkAuthNum(String authNum) {
        if (model.checkAuthNum(authNum)) {
            view.enableBtn(R.id.register_confirm_btn);
            view.showToast("인증이 완료되었습니다.");

        } else {
            view.showToast("인증 번호를 정확하게 입력해주세요.");
        }
    }

    @Override
    public void setUseAgree() {
        model.setCheckUseAgree();
    }

    @Override
    public void setPrivacyAgree() {
        model.setCheckPrivacyAgree();
    }

    @Override
    public void setGender(int id) {
        if (id == R.id.register_man_rb) {
            model.setMan();
        } else {
            model.setWoman();
        }
    }

    @Override
    public void signUp() {


        if (!model.checkNotNull()) {
            view.showToast("모두 입력해주세요.");
        } else if (!model.checkPasswordConfirm()) {
            view.showToast("2차 비밀번호가 같지않습니다.");
        } else if (!model.checkAgree()) {
            view.showToast("이용약관 동의를 모두 해주세요.");
        } else if (!model.checkBirthDay()) {
            view.showToast("생년월일을 정확하게 입력해주세요.");
        } else {
            model.signUp(new UserModel.SignUpCallbackListener() {
                @Override
                public void onSuccess() {
                    model.addFirestore(new UserModel.AddFirestoreCallbackListener() {
                        @Override
                        public void onSuccess() {
                            view.redirectLoginActivity();
                            view.showToast("회원가입에 성공하였습니다.");
                        }

                        @Override
                        public void onFail() {
                            view.showToast("회원가입에 실패하였습니다...");
                        }
                    });

                }

                @Override
                public void onFail() {
                    view.showToast("회원가입에 실패하였습니다...");
                }
            });
        }
    }

    @Override
    public void setBirthDay(String year, String month, String day) {
        model.setBirthDay(year, month, day);
    }

    @Override
    public void setName(String name) {
        model.setName(name);
    }

    @Override
    public void setPassword(String password) {
        model.setPassword(password);
    }

    @Override
    public void setPasswordConfirm(String passwordConfirm) {
        model.setPasswordConfirm(passwordConfirm);
    }

    @Override
    public void setAddress(String address) {
        model.setAddress(address);
    }

    @Override
    public void setCountryCode(String countryCode) {
        model.setCountryCode(countryCode);
    }
}
