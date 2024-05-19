-- drop database DB_MegaCompra;
Create database DB_MegaCompra;
use DB_MegaCompra;

create table Clientes(
	codigoCliente int not null,
	NITCliente varchar(10) not null,
	nombreCliente varchar(50)not null,
	apellidoCliente varchar(50) not null,
	direccionCliente varchar(250),
	telefonoCliente varchar(8),
	correoCliente varchar(45),
	primary key PK_Clientes(codigoCliente)
);

create table Proveedores(
	codigoProveedor int not null,
    NITproveedor varchar(10) not null,
    nombreProveedor varchar(60) not null,
    apellidoProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor(codigoProveedor)
);

create table CargoEmpleado(
	codigoCargoEmpleado int not null,
	nombreCargo varchar(45),
	descripcionCargo varchar(45),
	primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
);

create table TipoProducto(
	codigoTipoProducto int not null,
	descripcion varchar(45),
	primary key PK_codigoTipoProducto(codigoTipoProducto)
);

create table Compras(
	numeroDocumento int not null,
	fechaDocumento date,
	descripcion varchar (60),
	totalDocumento decimal(10,2),
	primary key PK_numeroDocumento(numeroDocumento)
);

-- Agregar Clientes
Delimiter $$
	create procedure sp_agregarCliente (in codigoCliente int, NITcliente varchar(10), in nombreCliente varchar(50), in apellidoCliente varchar(50),
    in direccionCliente varchar(250), in telefonoCliente varchar(8), in correoCliente varchar(45))
		Begin 
			Insert into Clientes (codigoCliente, NITCliente, nombreCliente, apellidoCliente, direccionCliente,
            telefonoCliente, correoCliente) 
            values (codigoCliente, NITCliente, nombreCliente, apellidoCliente, direccionCliente,
            telefonoCliente, correoCliente);
	end $$
Delimiter ;

call sp_AgregarCliente(1, '12345678', 'Crhistopher', 'Gomez', 'Zona 15', '12121212','cgomez@gmail.com');
call sp_AgregarCliente(2, '87654321', 'Edison', 'Donis', 'Zona 10', '34343434','eDonis@gmail.com');

-- Listar Clientes
Delimiter $$
	create procedure sp_ListarClientes()
		Begin
			select
            C.codigoCliente,
            C.NITCliente,
            C.nombreCliente,
            C.apellidoCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C;
		end $$
Delimiter ;

call sp_ListarClientes();

-- Buscar Clientes

Delimiter $$
	create procedure sp_BuscarClientes (in codCliente int)
		Begin
			select
            C.codigoCliente,
            C.NITCliente,
            C.nombreCliente,
            C.apellidoCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C
            where codigoCliente = codCliente;
		end $$
Delimiter ;

call sp_BuscarClientes(01);

-- Eliminar Cliente
Delimiter $$
	create procedure sp_EliminarClientes (in codCliente int)
		Begin 
			Delete from Clientes
				where codigoCliente = codCliente;
		End$$
Delimiter ;

call sp_EliminarClientes(2);

-- Editar Clientes
Delimiter $$
	Create procedure sp_EditarCliente(in _codigoCliente int, _NITcliente varchar(10), in _nombreCliente varchar(50), in _apellidoCliente varchar(50),
    in _direccionCliente varchar(250), in _telefonoCliente varchar(8), in _correoCliente varchar(45))
    Begin
		update Clientes
			set
            Clientes.codigoCliente = _codigoCliente,
            Clientes.NITCliente = _NITcliente,
            Clientes.nombreCliente = _nombreCliente,
            Clientes.apellidoCliente = _apellidoCliente,
            Clientes.direccionCliente = _direccionCliente,
            Clientes.telefonoCliente = _telefonoCliente,
            Clientes.correoCliente = _correoCliente
            where Clientes.codigoCliente = _codigoCliente;
	end $$
Delimiter ;

call sp_EditarCliente(1, '98989898', 'Alexander', 'Rojas', 'Zona 20', '45544554','aRojas@gmail.com');
call sp_ListarClientes();

-- Agregar Proveedores
Delimiter $$
	Create procedure sp_AgregarProveedores (in codigoProveedor int, in NITproveedor varchar(10), in nombreProveedor varchar(60),
    in apellidoProveedor varchar(60), in direccionProveedor varchar(150), in razonSocial varchar(60), in contactoPrincipal varchar(50), in paginaWeb varchar (50))
		Begin 
			Insert into Proveedores(codigoProveedor ,NITproveedor ,nombreProveedor ,apellidoProveedor ,direccionProveedor ,razonSocial ,contactoPrincipal,
            paginaWeb)
            values (codigoProveedor ,NITproveedor ,nombreProveedor ,apellidoProveedor ,direccionProveedor ,razonSocial ,contactoPrincipal,
            paginaWeb);
		end $$
Delimiter ;

call sp_AgregarProveedores(1,'65498732','Alex','Sumale','Zona 4','Vendedor','02321265','aSumale@gmail.com');
call sp_AgregarProveedores(2,'20155087','Aylin','Gomez','Zona 5','Vendedora','89742561','aGomez@gmail.com');

-- Listar Proveedores
Delimiter $$
	Create procedure sp_ListarProveedores()
		Begin
			Select
            P.codigoProveedor,
            P.NITproveedor,
            P.nombreProveedor,
            P.apellidoProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
            from Proveedores P;
		end $$
Delimiter ;

call sp_ListarProveedores();

-- Buscar Proveedores
Delimiter $$
	Create procedure sp_BuscarProveedor(in codProveedor int)
		Begin
			Select 
            P.codigoProveedor,
            P.NITproveedor,
            P.nombreProveedor,
            P.apellidoProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
            from Proveedores P
            where codigoProveedor = codProveedor;
		end $$
Delimiter ;

call sp_BuscarProveedor(1);

-- Eliminar Proveedores
Delimiter $$
	Create procedure sp_EliminarProveedor(in codProveedor int)
		Begin
			Delete from Proveedores
            where codigoProveedor = codProveedor;
	end$$
Delimiter ;

call sp_EliminarProveedor(2);

-- Editar Proveedores
Delimiter $$
	create procedure sp_EditarProveedor(in _codigoProveedor int, in _NITproveedor varchar(10), in _nombreProveedor varchar(60),
    in _apellidoProveedor varchar(60), in _direccionProveedor varchar(150), in _razonSocial varchar(60), in _contactoPrincipal varchar(50), in _paginaWeb varchar (50))
		Begin
			update Proveedores
				set
                Proveedores.codigoProveedor = _codigoProveedor,
                Proveedores.NITproveedor = _NITproveedor,
                Proveedores.nombreProveedor = _nombreProveedor,
                Proveedores.apellidoProveedor = _apellidoProveedor,
                Proveedores.direccionProveedor = _direccionProveedor,
                Proveedores.razonSocial = _razonSocial,
                Proveedores.contactoPrincipal = contactoPrincipal,
                Proveedores.paginaWeb = _paginaWeb
                where proveedores.codigoProveedor = _codigoProveedor;
	end $$
Delimiter ;

call sp_EditarProveedor(1,'07070707','Ronaldo','Dos Santos','Zona 13','Vendedor','07070707','rDosSantos@gmail.com');
call sp_ListarProveedores();

-- Agregar Cargo de Empleado
Delimiter $$
	create procedure sp_agregarCargoEmpleado (in codigoCargoEmpleado int, in nombreCargo varchar(45), in descripcionCargo varchar(45))
		begin
		insert into CargoEmpleado (codigoCargoEmpleado,nombreCargo,descripcionCargo) 
        values (codigoCargoEmpleado,nombreCargo,descripcionCargo);
	end $$
Delimiter ;
    
call sp_agregarCargoEmpleado (1,'Supervisor', 'Encargado de verificar');
call sp_agregarCargoEmpleado (2,'Gerente General', 'Maxima autorida');


-- Listar Cargo de Empleado
Delimiter $$
	create procedure sp_ListarCargoEmpleado()
		begin
			select
			CE.codigoCargoEmpleado,
			CE.nombreCargo,
			CE.descripcionCargo
			from CargoEmpleado CE;
	end $$
Delimiter ;

call sp_ListarCargoEmpleado();

-- Buscar Cargo de Empleado
Delimiter $$
	create procedure sp_BuscarCargoEmpleado (in codCargoEm int)
		begin
			select
			CE.codigoCargoEmpleado,
			CE.nombreCargo,
			CE.descripcionCargo
			from CargoEmpleado CE
			where codigoCargoEmpleado = codCargoEm;
	end $$
delimiter ;

call sp_BuscarCargoEmpleado(1);

-- Eliminar Cargo de Empleado
Delimiter $$
	create procedure sp_EliminarCargoEmpleado(in codCargoEm int)
		begin
			delete from CargoEmpleado
			where codigoCargoEmpleado = codCargoEm;
	end $$
delimiter ;

call sp_EliminarCargoEmpleado(2);

-- Editar Cargo de Empleado
Delimiter $$
	create procedure sp_EditarCargoEmpleado(in codEmpleado int, in nomCargo varchar(45), in deCargo varchar(45))
		begin
		update CargoEmpleado CargoEmpleado
			set
            CargoEmpleado.codigoCargoEmpleado = codEmpleado,
			CargoEmpleado.nombreCargo = nomCargo,
			CargoEmpleado.descripcionCargo = deCargo
			where codigoCargoEmpleado = codEmpleado;
	end $$
delimiter ;       
         
call sp_EditarCargoEmpleado(1, 'Supervisor','Encargado de verificar');
call sp_ListarCargoEmpleado();

-- Agregar Tipo de Producto
Delimiter $$
	create procedure Sp_agregarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
		begin
			insert into TipoProducto(codigoTipoProducto,descripcion) 
        values (codigoTipoProducto,descripcion);
	end $$
Delimiter ;

call Sp_agregarTipoProducto (1,'Alimento');
call Sp_agregarTipoProducto (2,'Electrodomestico');

-- Listar Tipo de Producto 
Delimiter $$
	create procedure sp_ListarTipoProducto()
		begin
			select
			T.codigoTipoProducto,
			T.descripcion
			from TipoProducto T;
	end $$
Delimiter ;

call sp_ListarTipoProducto();

-- Buscar Tipo de Producto
Delimiter $$
	create procedure sp_BuscarTipoProducto (in codTipoProducto int)
		begin
			select
			T.codigoTipoProducto,
			T.descripcion
			from TipoProducto T
			where codigoTipoProducto = codTipoProducto;
	end $$
delimiter ;

call sp_BuscarTipoProducto(2);

-- Eliminar Tipo de Empleado
Delimiter $$
	create procedure sp_EliminarTipoProducto(in codTipoProducto int)
		begin
			delete from TipoProducto
			where codigoTipoProducto = codTipoProducto;
	end $$
delimiter ;

call sp_EliminarTipoProducto(2);

-- Editar Tipo de Producto 
Delimiter $$
	Create procedure sp_EditarTipoProducto(in codProducto int, in descripcion varchar(45))
		begin
			update TipoProducto T
			set
            T.codigoTipoProducto = codProducto,
			T.descripcion = descripcion
			where codigoTipoProducto = codProducto;
	end $$
delimiter ;
          
call sp_EditarTipoProducto(1,'Bebida');
call sp_ListarTipoProducto();

-- Agregar Compras
Delimiter $$
	create procedure Sp_agregarCompras (in numeroDocumento int, in fechaDocumento date, in descripcion varchar (60), in totalDocumento decimal(10,2))
		begin
		insert into Compras (numeroDocumento,fechaDocumento,descripcion,totalDocumento) 
        values (numeroDocumento,fechaDocumento,descripcion,totalDocumento);
	end $$
Delimiter ;

call Sp_agregarCompras (1, '2024-04-26', 'Compra en proceso', 10.50);
call Sp_agregarCompras (2, '2024-04-24', 'compra realizada', 89.06);

-- Listar Compras
Delimiter $$
	create procedure sp_ListarCompras()
		begin
			select
			Cp.numeroDocumento,
			Cp.fechaDocumento,
			Cp.descripcion,
			Cp.totalDocumento
		from Compras Cp;
	end $$
Delimiter ;

call sp_ListarCompras();

-- Buscar Compras
Delimiter $$
	create procedure sp_BuscarCompras (in codCompra int)
		begin
			select
			Cp.numeroDocumento,
			Cp.fechaDocumento,
			Cp.descripcion,
			Cp.totalDocumento
			from Compras Cp
			where numeroDocumento = codCompra;
	end $$
delimiter ;

call sp_BuscarCompras(1);

-- Eliminar Compras 
Delimiter $$
	create procedure sp_EliminarCompras(in codCompra int)
		begin
			delete from Compras
			where numeroDocumento = codCompra;
	end $$
delimiter ;

call sp_EliminarCompras(1);

-- Eliminar Compras 
Delimiter $$
	create procedure sp_EditarCompras(in numDocumento int, in fDocument date, in descrip varchar (60), in toDocumento decimal(10,2))
		begin
			update Compras Cp
				set
				Cp.fechaDocumento = fDocument,
				Cp.descripcion = descrip,
				Cp.totalDocumento = toDocumento
				where numeroDocumento = numDocumento;
		end $$
delimiter ;    
            
call sp_EditarCompras(2, '2024-03-15' , 'Compra Realizada', 15.20);
call sp_ListarCompras();

set global time_zone = '-6:00'