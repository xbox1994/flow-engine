const myflow ={
    "type":"PROCESS",
    "id":"testProcess",
    "name":"测试流程",
    "elements":[
        {
            "id":"start",
            "type":"START",
            "name":"开始节点",
            "config":{}
        },
        {
            "id":"line_start_to_decision",
            "name":"",
            "type":"SEQUENCE_FLOW",
            "sourceId":"start",
            "targetId":"decision1"
        },
        {
            "id":"decision1",
            "type":"DECISION",
            "name":"判断节点",
            "config":{

            }
        },
        {
            "id":"line_decision1_to_task1",
            "type":"SEQUENCE_FLOW",
            "sourceId":"decision1",
            "targetId":"task1",
            "config":{
                "condition":"pullMode==1&&supplyType==2"
            }
        },
        {
            "id":"line_decision1_to_task2",
            "type":"SEQUENCE_FLOW",
            "sourceId":"decision1",
            "targetId":"task2",
            "config":{
                "condition":"pullMode==2&&supplyType==3"
            }
        },
        {
            "id":"line_decision1_to_task2",
            "type":"SEQUENCE_FLOW",
            "sourceId":"decision1",
            "targetId":"end",
            "config":{
            }
        },
        {
            "id":"task1",
            "type":"SERVICE_TASK",
            "name":"任务1",
            "config":{
                "implementation":"JavaBean",
                "beanName":"task1Service"
            }
        },
        {
            "id":"task2",
            "type":"SERVICE_TASK",
            "name":"任务2",
            "config":{
                "implementation":"JavaBean",
                "beanName":"task2Service"
            }
        },
        {
            "id":"line_task2_to_task3",
            "type":"SEQUENCE_FLOW",
            "sourceId":"task2",
            "targetId":"task3",
            "config":{
            }
        },
        {
            "id":"task3",
            "type":"USER_TASK",
            "name":"任务3(人工)",
            "config":{
            }
        },
        {
            "id":"line_task2_to_task3",
            "type":"SEQUENCE_FLOW",
            "sourceId":"task2",
            "targetId":"task3",
            "config":{
            }
        },
        {
            "id":"line_task3_to_callActivity",
            "type":"SEQUENCE_FLOW",
            "sourceId":"task3",
            "targetId":"callActivity",
            "config":{
            }
        },
        {
          "type": "CALL_ACTIVITY",
          "id": "callActivity",
          "name": "子流程节点",
          "config": {
            "processId": "childTestProcess"
          }
        },
        {
            "id":"line_callActivity_to_end",
            "type":"SEQUENCE_FLOW",
            "sourceId":"callActivity",
            "targetId":"end",
            "config":{
            }
        },
        {
            "id":"line_task1_to_end",
            "type":"SEQUENCE_FLOW",
            "sourceId":"task1",
            "targetId":"end"
        },
        {
            "id":"end",
            "name":"结束节点",
            "type":"END"
        }
    ]
}