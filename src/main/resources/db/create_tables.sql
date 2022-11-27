create table tbl_project (id bigint not null auto_increment, client_id integer not null, description varchar(255), ending_date datetime(6), name varchar(255), project_type integer, starting_date datetime(6), state integer, primary key (id));
create table tbl_resource (id bigint not null auto_increment, resource_identifier integer not null, resource_id bigint, primary key (id));
create table tbl_task (id bigint not null auto_increment, description varchar(255), ending_date datetime(6), estimated_hours double precision, name varchar(255), starting_date datetime(6), state integer, project_id bigint, primary key (id));
alter table tbl_resource add constraint FK4s5f1hvp32jx36fr9efrm336i foreign key (resource_id) references tbl_task (id);
alter table tbl_task add constraint FKfm9u9lv2gnry13rsuuuadaxoa foreign key (project_id) references tbl_project (id);
