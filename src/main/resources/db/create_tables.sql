create table tbl_client (id bigint not null auto_increment, primary key (id));
create table tbl_project (id bigint not null auto_increment, description varchar(255), ending_date datetime(6), name varchar(255), project_type integer, starting_date datetime(6), state integer, primary key (id));
create table tbl_task (id bigint not null auto_increment, description varchar(255), ending_date datetime(6), estimated_hours double precision, name varchar(255), starting_date datetime(6), state integer, project_id bigint, primary key (id));
create table tbl_ticket (id bigint not null auto_increment, description varchar(255), priority integer, project_name datetime(6), project_version datetime(6), state integer, title varchar(255), client_id bigint, primary key (id));
create table ticket_tasks (task_id bigint not null, ticket_id bigint not null);
alter table tbl_task add constraint FKfm9u9lv2gnry13rsuuuadaxoa foreign key (project_id) references tbl_project (id);
alter table tbl_ticket add constraint FK6yk5t62xv1flw1obydgtdkgld foreign key (client_id) references tbl_client (id);
alter table ticket_tasks add constraint FKo2iwa1siu41wrvkmcuubsn79p foreign key (ticket_id) references tbl_ticket (id);
alter table ticket_tasks add constraint FKlxpon4be1d1yyivog2c7a4tia foreign key (task_id) references tbl_task (id);
