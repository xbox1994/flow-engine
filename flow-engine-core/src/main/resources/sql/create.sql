create table if not exists flow_activity_task
(
    id            bigint       not null
        primary key,
    proc_inst_id  bigint       null,
    execution_id  bigint       null,
    activity_id   varchar(64)  null,
    activity_name varchar(255) null
);

create index activity_task_proc_inst_id_index
    on flow_activity_task (proc_inst_id);

create table if not exists flow_deployment
(
    id                   bigint    not null
        primary key,
    deploy_time          timestamp not null,
    parent_deployment_id bigint    null,
    constraint deployment_id_uindex
        unique (id)
);

create table if not exists flow_execution
(
    id                 bigint       not null
        primary key,
    proc_instance_id   bigint       not null,
    proc_definition_id bigint       not null,
    biz_id             varchar(255) null,
    activity_id        varchar(255) not null,
    activity_name      varchar(255) null,
    parent_id          bigint       null,
    variables          longtext     null,
    constraint execution_id_uindex
        unique (id)
);

create table if not exists flow_process_definition
(
    id            bigint                              not null
        primary key,
    name          varchar(255)                        null,
    `key`         varchar(255)                        not null comment 'apiName',
    deployment_id bigint                              not null,
    resource_name varchar(255)                        null comment '流程定义资源名称',
    version       int                                 null,
    _create_time  timestamp default CURRENT_TIMESTAMP not null,
    _update_time  timestamp default CURRENT_TIMESTAMP not null,
    constraint process_definition_id_uindex
        unique (id)
);

create table if not exists flow_process_event_log
(
    id           bigint       not null
        primary key,
    proc_inst_id bigint       not null,
    execution_id bigint       null,
    event        varchar(255) not null,
    node_id      varchar(255) null,
    node_name    varchar(255) null,
    time         timestamp    not null,
    exception    longtext     null,
    variables    longtext     null,
    extra        longtext     null
);

create index process_event_log_proc_inst_id_index
    on flow_process_event_log (proc_inst_id);

create table if not exists flow_process_instance
(
    id                     bigint       not null
        primary key,
    execution_id           bigint       not null,
    process_definition_id  bigint       not null,
    process_definition_key varchar(255) not null,
    start_activity_id      varchar(255) null comment '流程开始节点ID',
    end_activity_id        varchar(255) null comment '流程结束节点ID',
    start_time             timestamp    not null,
    end_time               varchar(255) null comment '结束时间',
    status                 varchar(64)  not null,
    remark                 varchar(255) null comment '备注（原因等）',
    constraint process_instance_id_uindex
        unique (id)
);

create table if not exists flow_resource
(
    id            bigint       not null
        primary key,
    name          varchar(255) null,
    deployment_id bigint       null,
    bytes         longblob     null,
    constraint resource_id_uindex
        unique (id)
);

