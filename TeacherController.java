package controller;

import java.util.ArrayList;

import model.TeacherDTO;

public class TeacherController {
    // TeacherDTO 목록을 담당할 ArraList 필드 list
    private ArrayList<TeacherDTO> list;
    // 추가될 TeacherDTO의 id를 담당할 int 필드 id
    private int id;

    // 1. 생성자
    public TeacherController() {
        // ArrayList 필드 list 초기화
        list = new ArrayList<>();
        id = 1;

        // teacherDTO 객체를 생성해서
        // list에 추가해준다.
        TeacherDTO t = new TeacherDTO();
        t.setId(id++);
        t.setName("교사1");
        t.setUsername("t1");
        t.setPassword("1");
        t.setGroup(1);
        list.add(t);

        t = new TeacherDTO();
        t.setId(id++);
        t.setName("교사2");
        t.setUsername("t2");
        t.setPassword("2");
        t.setGroup(2);
        list.add(t);

    }
    // 2. 사용자로부터 받아온 TeacherDTO객체 파라미터에
    // 나머지 정보를 추가해서 리스트에 추가하는
    // add()

    public void add(TeacherDTO t) {
        // 사용자가 이름, username, password, group 까지 입력했고
        // 비어있는 필드는 id 밖에 없기 때문에
        // id만 set해준다.
        t.setId(id++);

        // id까지 추가된 TeacherDTO 객체 t를 list에 추가해준다.
        list.add(t);
    }

    // 3. 사용자가 입력한 TeacherDTO 객체의 username과 password가 일치하는
    // list의 엘리먼트를 리턴해주는 auth()
    public TeacherDTO auth(TeacherDTO user) {
        for (TeacherDTO t : list) {
            if (t.getUsername().equals(user.getUsername()) && t.getPassword().equals(user.getPassword())) {
                return t;
            }
        }
        return null;
    }

    // 4. 사용자가 입력한 username이 중복인지 체크하는 validateUsername();
    public boolean validareUsername(TeacherDTO user) {
        for (TeacherDTO t : list) {
            if (t.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    // 5. 사용자가 입력한 group이 중복인지 체크하는 validateGroup()
    public boolean validateGroup(TeacherDTO user) {
        for (TeacherDTO t : list) {
            if (t.getGroup() == user.getGroup())
                return true;
        }

        return false;
    }

}