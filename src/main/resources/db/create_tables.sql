create table tbl_project (id bigint not null auto_increment, client_id integer not null, description varchar(255), ending_date datetime(6), name varchar(255), project_type integer, starting_date datetime(6), state integer, version_id integer not null, primary key (id));
create table tbl_resource (id bigint not null auto_increment, resource_identifier integer not null, resource_id bigint, primary key (id));
create table tbl_role_to_resource_id (id bigint not null, resourceid bigint, role varchar(255) not null, primary key (id, role));
create table tbl_task (id bigint not null auto_increment, description varchar(255), ending_date datetime(6), estimated_hours double precision, name varchar(255), previous_task_id bigint, priority integer, real_ending_date datetime(6), starting_date datetime(6), state integer, project_id bigint, primary key (id));
alter table tbl_resource add constraint FK4s5f1hvp32jx36fr9efrm336i foreign key (resource_id) references tbl_task (id);
alter table tbl_role_to_resource_id add constraint FKh6v173xiyarynbas9o3dcfhxq foreign key (id) references tbl_project (id);
alter table tbl_task add constraint FKfm9u9lv2gnry13rsuuuadaxoa foreign key (project_id) references tbl_project (id);
