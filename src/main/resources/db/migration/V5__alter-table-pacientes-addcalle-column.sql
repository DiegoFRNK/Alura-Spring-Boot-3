alter table pacientes
    add calle varchar(100) not null after telefono;

alter table medicos
    add provincia varchar(100) not null after ciudad;
alter table medicos
    add codigo_Postal varchar(100) not null after provincia;
alter table medicos
    add urbanizacion varchar(100) not null after codigo_Postal;