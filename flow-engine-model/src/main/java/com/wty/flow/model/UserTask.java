package com.wty.flow.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTask extends Activity {

    public UserTask() {
        this.manual = true;
    }

}
