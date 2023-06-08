package handler;

import com.wty.flowengine.engine.common.api.JavaDelegate;
import com.wty.flowengine.engine.common.api.VariableScope;
import com.wty.flowengine.engine.common.utils.JsonUtil;

public class DocumentUpdateHandler implements JavaDelegate {
    @Override
    public void execute(VariableScope variableScope) {
        Object data = variableScope.getVariable("data");
        System.out.println("更新数据：" + JsonUtil.toJson(data));
        System.out.println("更新数据成功！");
    }
}
