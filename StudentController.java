package controller;

// 뷰어의 요청에 맞춰서 데이터를 가공해주는 컨트롤러
// 원래대로라면 데이터베이스를 왔다갔다 하면서 데이터를 CRUD 해야하지만
// 우리는 데이터베이스가 없으므로 
// 이 안에 ArrayList<>를 이용하여 유사 데이터베이스를 만들어보게 될 것이다. 
import java.util.ArrayList;
import java.util.Random;

import model.StudentDTO;

public class StudentController {
    // 컨트롤러에서 데이터베이스 대신 사용할
    // ArrayList<> 객체
    // 단, 해당 어레이리스트 객체는 외부에서
    // 값을 넘겨주거나, 값을 불러올 일이 없으므로
    // 겟터 셋터는 존재하지 않는다.

    private ArrayList<StudentDTO> list;
    // StudentDTO 를 추가할 때마다 1씩 늘어서
    // database의 기능을 하나 대신해줄 int 변수 id
    private int id;

    // 1. StudentController() 생산자
    public StudentController() {
        // 컨트롤러 생산자를 통해서 list의 초기화와
        // 초기 데이터 입력을 한다.

        // list의 초기화
        list = new ArrayList<>();

        // id의 초기화
        id = 1;

        // 여러개의 Student 객체를 만들어서
        // 데이터를 입력해주고
        // 그리고 list에 추가해준다.

        for (int i = 0; i < 5; i++) {
            StudentDTO s = new StudentDTO();
            s.setId(id++);
            s.setName("학생" + i);
            s.setGroup((i % 2) + 1);
            s.setInputSwitch(i % 3 == 0);
            s.setUsername("s" + i);
            // int 값을 String으로 바꿀 때에는
            // 값 + ""으로 해줘도 되지만
            // 가장 정식적인 방법은
            // Integer.toString(값) 이 가장 정식적인 방법이다.
            s.setPassword(Integer.toString(i));
            if (s.isInputSwitch()) {
                Random random = new Random();
                s.setKorean(random.nextInt(101));
                s.setEnglish(random.nextInt(101));
                s.setMath(random.nextInt(101));

            }

            list.add(s);
        }

    }

    // 2. 사용자가 입력한 로그인 정보와 일치하는 객체를 찾아내어
    // 해당 객체를 리턴해주고 일치하는 객체가 없으면 null을 리턴해주는
    // auth() 메소드
    public StudentDTO auth(StudentDTO user) {
        for (StudentDTO s : list) {
            if (s.getUsername().equals(user.getUsername()) && s.getPassword().equals(user.getPassword())) {
                return s;
            }

        }
        return null;
    }

    // 3. 파라미터로 넘어온 group 과 같은 group 에 속하는 StudentDTO 객체를
    // 어레이리스트에 담아서 return 해주는
    // selectByGrouopId() 메소드

    public ArrayList<StudentDTO> selectByGroup(int group) {
        ArrayList<StudentDTO> temp = new ArrayList<>();
        for (StudentDTO s : list) {
            if (s.getGroup() == group) {
                temp.add(s);
            }
        }

        return temp;
    }

    // 4. 사용자로부터 입력받은 StudentDTO 객체를
    // 리스트에 추가해주는 add() 메소드
    public void add(StudentDTO s) {
        s.setId(id++);
        s.setInputSwitch(false);

        list.add(s);
    }

    // 5. 사용자가 입력한 username과 같은 username이 있으면
    // true를 리턴하는 validateUsername() 메소드

    public boolean validateUsername(String username) {
        for (StudentDTO s : list) {
            if (s.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    // 6. 사용자가 요청한 id를 가진 StudentDTO 객체를 return 해주는
    // selectOne() 메소드
    public StudentDTO selectOne(int id) {
        for (StudentDTO s : list) {
            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

    // 7. 사용자가 넘겨준 StudentDTO 객체를 수정하는 update() 메소드
    public void update(StudentDTO updated) {
        for (StudentDTO s : list) {
            if (s.getId() == updated.getId()) {
                s.setKorean(updated.getKorean());
                s.setEnglish(updated.getEnglish());
                s.setMath(updated.getMath());
                s.setInputSwitch(true);
            }
        }
    }

    // 8. 사용자가 넘겨준 group과 일치하는 객체들 중에서 아직 성적 입력이 안된 객체들만 뽑아내는
    // selectByInputSwitch()
    public ArrayList<StudentDTO> selectByInputSwitch(int group) {
        ArrayList<StudentDTO> list = selectByGroup(group);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isInputSwitch()) {
                list.remove(i);
            }
        }
        return list;
    }

}
