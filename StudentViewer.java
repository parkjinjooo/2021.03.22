package viewer;
// 학생과 관련된 UI를 담당하는 StudentViewer 클래스

import java.util.ArrayList;
import java.util.Scanner;

import controller.StudentController;
import model.StudentDTO;
import util.ScannerUtil;

public class StudentViewer {
    // 단, 이 클래스에서는
    // 우리가 입력도 처리해야되고
    // Controller가 반드시 필요하다.
    // 그러므로 private 필드로 만들어주고
    // 대신 getter/setter는 만들어주지 않으면
    // 뷰어가 혼자 갖고 있는 형태가 되므로
    // 외부로부터 접근이 불가능하다.

    private Scanner scanner;
    private StudentController controller;
    // 로그인한 학생의 정보를 저장할 StudentDTO 객체
    private StudentDTO logIn;

    // 1이면 모든 학생 보여주기, 2 이면 성적 입력 안된 학생 보여주기를 뜻하는
    // int 상수
    private final int VALUE_ALL = 1;
    private final int VALUE_NO_INPUT = 2;
    // 과목의 숫자를 저장하는 상수 SUBJECT_SIZE
    private final int SUBJECT_SIZE = 3;
    // 점수의 최저점과 최고점을 저장하는 상수
    private final int SCORE_MIN = 0;
    private final int SCORE_MAX = 100;

    // 생성자를 통해서 각각의 필드를 초기화해준다.
    public StudentViewer() {
        scanner = new Scanner(System.in);
        controller = new StudentController();
    }

    // 1. 회원 가입을 담당하는 메소드
    public void register() {
        // 1-1. 학생 객체 생성
        StudentDTO s = new StudentDTO();

        // 1-2. 입력에서 사용할 String 객체 message 선언
        String message;

        // 1-2. 학생이 사용하고자 하는 아이디를 입력 받는다.
        message = new String("사용하실 아이디를 입력해주세요");
        String username = ScannerUtil.nextLine(scanner, message);

        // 1-3. 사용자가 입력한 아이디가 유효한 아이디인지 체크하고
        // 유효하지 않으면 다시 입력을 받는다.

        while (controller.validateUsername(username)) {
            System.out.println("이미 중복된 아이디입니다. ");
            username = ScannerUtil.nextLine(scanner, message);
        }

        // 1-4. 미리 만들어놓은 학생 객체에 username을 추가해준다.
        s.setUsername(username);

        // 1-5. 사용자의 비밀번호를 입력받아서 학생 객체에 추가해준다.
        message = new String("사용하실 비밀번호를 입력해주세요.");
        s.setPassword(ScannerUtil.nextLine(scanner, message));

        // 1-6. 사용자의 이름을 입력받아서 학생 객체에 추가해준다.
        message = new String("이름을 입력해주세요.");
        s.setName(ScannerUtil.nextLine(scanner, message));

        // 1-7. 사용자의 소속반을 입력받아서 학생 객체에 추가해준다.
        message = new String("소속반을 입력해주세요.");
        s.setGroup(ScannerUtil.nextInt(scanner, message));

        // 1-8. 정보가 들어가 있는 헉생객체를 컨트롤러의 add() 한다.
        controller.add(s);
    }

    // 2. 로그인을 담당하는 메소드
    public boolean logIn() {
        // 2-1. 로그인한 객체의 정보를 담을 학생 객체 s를 생성
        StudentDTO s = new StudentDTO();

        // 2-2. 출력에 사용할 메시지를 담을 String 개체 message 선언
        String message;

        // 2-3. 사용자로부터 아이디를 입력 받는다.
        message = new String("아이디를 입력해주세요");
        s.setUsername(ScannerUtil.nextLine(scanner, message));

        // 2-4. 사용자로부터 비밀번호를 입력받는다.
        message = new String("비밀번호를 입력해주세요");
        s.setPassword(ScannerUtil.nextLine(scanner, message));

        // 2-5. s에 해당 정보를 담아서 컨트롤러에 보내주고 결과값은 학생객체 result에 담아준다.
        StudentDTO result = controller.auth(s);

        // 2-6. 만약 결과가 null 이면 다시 입력을 받거나
        // 아니면 뒤로갈 지를 물어본다.
        while (result == null) {
            System.out.println("잘못 입력하셨습니다.");
            System.out.println("다시 시도하시겠습니까? y/n");
            String agree = ScannerUtil.nextLine(scanner, message);
            if (!agree.equalsIgnoreCase("y")) {
                return false;
            }

            message = new String("아이디를 입력해주세요");
            s.setUsername(ScannerUtil.nextLine(scanner, message));

            message = new String("비밀번호를 입력해주세요");
            s.setPassword(ScannerUtil.nextLine(scanner, message));

            result = controller.auth(s);
        }

        logIn = result;
        return true;
    }

    // 3. 학생 사용자가 로그아웃될 때,
    // 호출되서 logIn 객체를 null로 만들어주는 메소드
    public void logOut() {
        System.out.println("로그아웃 되었습니다.");
        logIn = null;
    }

    // 4. 학생 목록을 보여주는 메소드
    // 이 메소드는 파라미터로 로그인한 학생 객체와 어떤 목록인지를 지정한 int type을 넘겨받는다.
    // 만약 파라미터로 받은 로그인한 객체의 값이 null 이면 교사가 로그인해서 실행한 걸로 생각하면 된다.
    // int type의 경우 VALUE_ALL 이면 모든 학생 보여주기 하고, VALUE_NO_INPUT이면 아직 성적입력이 안된 학생으로
    // 생각하면
    // 된다.
    public void printList(int group, int type) {
        ArrayList<StudentDTO> list;
        if (logIn != null) {
            list = controller.selectByGroup(logIn.getGroup());
        } else {
            if (type == VALUE_ALL) {
                list = controller.selectByGroup(group);
            } else {
                list = controller.selectByInputSwitch(group);
            }
        }
        while (true) {
            // 3-1. 목록을 보여준다.
            System.out.println("번호\t이름\t성적 입력 여부");
            for (StudentDTO s : list) {
                // 성적 입력 여부를 X/O 로 보여줄 String 객체 input
                String input;
                if (s.isInputSwitch()) {
                    input = new String("O");
                } else {
                    input = new String("X");
                }

                System.out.printf("%2d번\t%s\t%s\n", s.getId(), s.getName(), input);
            }

            // 3-2. 개별보기할 아이디를 입력 받는다.
            String message = new String("개별 보기할 번호를 입력하시거나 뒤로 가실려면 0을 입력해주세요.");
            int id = ScannerUtil.nextInt(scanner, message);

            // 3-3. 입력한 번호로 controller의 selectOne을 살행한다.
            StudentDTO s = controller.selectOne(id);

            // 3-4. 번호가 유효한 번호인지 확인하고 유효하지 않으면 다시 입력을 받는다.
            while (!(id == 0 || list.contains(s))) {
                System.out.println("잘못 입력하셨습니다.");
                id = ScannerUtil.nextInt(scanner, message);
                s = controller.selectOne(id);

            }

            // 3-5. 만약 개별보기를 선택했으면 해당 메소드를 호출해주고
            // 아니면 break 한다.

            if (id == 0) {
                break;
            } else {
                printOne(id);
            }
        }

    }

    // 4. 학생 개개인을 보여주는 메소드
    public void printOne(int id) {
        StudentDTO s = controller.selectOne(id);
        System.out.printf("번호: %2d번 이름: %s\n", s.getId(), s.getName());
        System.out.printf("반: %2d반\n", s.getGroup());
        if (s.isInputSwitch()) {
            int sum = s.getKorean() + s.getEnglish() + s.getMath();
            double average = sum / (double) SUBJECT_SIZE;
            System.out.printf("국어: %03d점 영어: %03d점 수학: %03d점\n", s.getKorean(), s.getEnglish(), s.getMath());
            System.out.printf("총점: %03d점 평균: %06.2f점\n", sum, average);

        } else {
            System.out.println("아직 입력된 성적이 존재하지 않습니다.");
        }
        // 학생 로그인 객체를 파라미터로 받아서
        // null일 경우에는 교사로 판단하고
        // 1. 입력/수정 2. 뒤로가기
        // 를 보여주고
        // 그 외에는
        // 1. 뒤로가기 를 보여준다.

        // logIn 값에 따라서 값이 달라지는 String message
        String message;
        // 로그인이 null 이면 1. 입력/수정 2. 뒤로가기 그 외에는 1. 뒤로가기 message의 값으로 정해진다.
        if (logIn == null) {
            message = new String("1. 입력/수정 2. 뒤로가기");
        } else {
            message = new String("1. 뒤로가기");

        }
        // 만약, message 2. 이라는 글자가 들어가면
        // 1~2까지 입력을 받고, 그 외에는 1만 입력을 받게 된다.

        // 사용자의 입력값을 저장할 int userChoice
        int userChoice;

        if (message.contains("2")) {
            userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
            if (userChoice == 1) {
                // 수정 메소드 살행
                showUpdate(id);
                printOne(id);
            }
        } else {
            userChoice = ScannerUtil.nextInt(scanner, message, 1, 1);

        }

    }

    // 5. 교사일때 수정 화면을 보여주는 메소드
    public void showUpdate(int id) {
        StudentDTO s = controller.selectOne(id);
        String message;

        message = new String("국어점수를 입력해주세요");
        s.setKorean(ScannerUtil.nextInt(scanner, message, SCORE_MIN, SCORE_MAX));

        message = new String("영어점수를 입력해주세요");
        s.setEnglish(ScannerUtil.nextInt(scanner, message, SCORE_MIN, SCORE_MAX));

        message = new String("수학점수를 입력해주세요");
        s.setMath(ScannerUtil.nextInt(scanner, message, SCORE_MIN, SCORE_MAX));

        controller.update(s);
    }

}
