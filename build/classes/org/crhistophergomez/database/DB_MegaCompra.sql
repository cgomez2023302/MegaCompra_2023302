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

Create table Productos(
	codigoProducto int not null,
    descripcion varchar (45),
    precioUnitario decimal (10, 2), 
    precioDocena decimal (10, 2),
	precioMayor decimal (10, 2), 
    imagenProducto varchar (45),
    existencia int, 
    codigoTipoProducto int, 
    codigoProveedor int,
    primary key PK_codigoProducto(codigoProducto), 
    constraint FK_producto_tipoProducto foreign key (codigoTipoProducto)
		references TipoProducto (codigoTipoProducto),
	constraint FK_producto_Proveedor foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
);

Create table EmailProveedor(
	codigoEmailProveedor int not null, 
    emailProveedor varchar (50),
    descripcion varchar (100),
    codigoProveedor int,
    primary key PK_codigoEmailProveedor(codigoEmailProveedor),
    constraint FK_emailProveedor_Proveedor foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
);

Create table TelefonoProveedor(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar (8),
    numeroSecundario varchar (8),
    observaciones varchar (45),
    codigoProveedor int, 
    primary key PK_codigoTelefonoProveedor(codigoTelefonoProveedor),
	constraint FK_telefonoProveedor_Proveedor foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
);

Create table DetalleCompra(
	codigoDetalleCompra int not null,
    costoUnitario decimal (10, 2),
    cantidad int, 
    codigoProducto int,
    numeroDocumento int, 
    primary key PK_codigoDetalleCompra(codigoDetalleCompra), 
    constraint FK_codigoProducto_Producto foreign key (codigoProducto)
		references Productos (codigoProducto),
	constraint FK_numeroDocumento_Compra foreign key (numeroDocumento)
		references Compras (numeroDocumento)
);

Create table Empleados(
	codigoEmpleado int not null,
    nombresEmpleado varchar (50),
    apellidosEmpleado varchar (50),
    sueldo decimal (10, 2), 
    direccion varchar (150),
    turno varchar (15),
    codigoCargoEmpleado int,
    primary key PK_codigoEmpleado(codigoEmpleado),
    constraint FK_codigoCargoEmpleado_CargoEmpleado foreign key (codigoCargoEmpleado)
		references CargoEmpleado (codigoCargoEmpleado)
);

Create table Factura(
	numeroFactura int not null,
    estado varchar (50),
    totalFactura decimal (10, 2),
    fechaFactura varchar (45),
    codigoCliente int,
    codigoEmpleado int,
    primary key PK_numeroFactura(numeroFactura),
    constraint FK_codigoCliente_Clientes foreign key (codigoCliente)
		references Clientes (codigoCliente),
	constraint FK_codigoEmpleado_Empleados foreign key (codigoEmpleado)
		references Empleados (codigoEmpleado)
);

Create table DetalleFactura(
	codigoDetalleFactura int not null, 
    precioUnitario decimal (10, 2),
    cantidad int, 
    numeroFactura int,
    codigoProducto int, 
    primary key PK_codigoDetalleFactura(codigoDetalleFactura),
    constraint FK_numeroFactura_Factura foreign key (numeroFactura)
		references Factura (numeroFactura),
	constraint FK_codigoProductoFactura_Productos foreign key (codigoProducto)
		references Productos (codigoProducto)
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

-- Editar Compras 
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

-- Agregar Productos
Delimiter $$
	Create procedure sp_agregarProducto(in codigoProducto int, in descripcion varchar (45), in precioUnitario decimal (10, 2), in precioDocena decimal (10, 2), in precioMayor decimal (10, 2), in imagenProducto varchar(45), in existencia int, in codigoTipoProducto int, in codigoProveedor int)
		Begin
			Insert Into Productos(codigoProducto, descripcion, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoTipoProducto, codigoProveedor)
			values (codigoProducto, descripcion, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoTipoProducto, codigoProveedor);
		End$$
Delimiter ;

call sp_agregarProducto(1, 'Latas de sopa', 10.00, 7.5, 5.00, ':v', 1300, 1, 1);
call sp_agregarProducto(2, 'Botellas de agua', 4.99, 3.00, 2.50, ':v', 2000, 1, 1);

-- Listar Productos
Delimiter $$
	Create procedure sp_listarProducto()
		Begin
			Select 
				p.codigoProducto, 
                p.descripcion,
                p.precioUnitario,
                p.precioDocena, 
                p.precioMayor, 
                p.imagenProducto, 
                p.existencia, 
                p.codigoTipoProducto, 
                p.codigoProveedor
			from Productos p;
		End$$
Delimiter ;

call sp_listarProducto();

-- Buscar Productos
Delimiter $$
	Create procedure sp_buscarProducto(in codProducto int)
		Begin
			Select 
				p.codigoProducto, 
                p.descripcion,
                p.precioUnitario,
                p.precioDocena, 
                p.precioMayor, 
                p.imagenProducto, 
                p.existencia, 
                p.codigoTipoProducto, 
                p.codigoProveedor
			from Productos p
            where codigoProducto = codProducto;
		End$$
Delimiter ;

call sp_buscarProducto(2);

-- Eliminar Producto
Delimiter $$
	create procedure sp_eliminarProducto(in codProducto int)
		begin
			delete from Productos
			where codigoProducto = codProducto;
	end $$
delimiter ;

call sp_eliminarProducto(1);
call sp_listarProducto();

-- Editar Producto 
Delimiter $$
	create procedure sp_editarProducto(in codProducto int, in descrip varchar(45), in precioU decimal (10, 2), in precioD decimal(10, 2), in precioM decimal(10, 2), in imgProducto varchar(45), in existenciaP int, in codTipoProducto int, in codProveedor int)
		begin
			update Productos p
				set
				p.descripcion = descrip,
				p.precioUnitario = precioU,
				p.precioDocena = precioD,
                p.precioMayor = precioM,
                p.imagenProducto = imgProducto,
                p.existencia = existenciaP, 
                p.codigoTipoProducto = codTipoProducto,
                p.codigoProveedor = codProveedor
				where codigoProducto = codProducto;
		end $$
delimiter ;    
            
call sp_editarProducto(1, 'Bolsas de papas', 5.00, 2.99, 2.00, ':v', 1750, 2, 2);
call sp_listarProducto();

-- Agregar email proveedor
Delimiter $$
	Create procedure sp_agregarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar (50), in descripcion varchar (100), in codigoProveedor int)
		Begin
			Insert Into EmailProveedor(codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor)
			values (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor);
		End$$
Delimiter ;

call sp_agregarEmailProveedor(1, 'cgomez-2023302@kinal.edu.gt', 'Email de cuenta educativa', 1);
call sp_agregarEmailProveedor(2, 'arojas-2023302@kinal.edu.gt', 'Email de cuenta educativa', 1);

-- Listar email Proveedor 

Delimiter $$
	create procedure sp_listarEmailProveedor()
		begin
			select
			EP.codigoEmailProveedor,
			EP.emailProveedor,
			EP.descripcion,
			EP.codigoProveedor
		from EmailProveedor EP;
	end $$
Delimiter ;

call sp_listarEmailProveedor();

-- Buscar email Proveedor

Delimiter $$
	create procedure sp_buscarEmailProvedor (in codEP int)
		begin
			select
			EP.codigoEmailProveedor,
			EP.emailProveedor,
			EP.descripcion,
			EP.codigoProveedor
			from EmailProveedor EP
			where codigoEmailProveedor = codEP;
	end $$
delimiter ;

call sp_buscarEmailProvedor(1);

-- Elimiar email Proveedor

Delimiter $$
	create procedure sp_eliminarEmailProveedor(in codEP int)
		begin
			delete from EmailProveedor
			where codigoEmailProveedor = codEP;
	end $$
delimiter ;

call sp_eliminarEmailProveedor(2); 
call sp_listarEmailProveedor();

-- Editar email Proveedor

Delimiter $$
	Create procedure sp_editarEmailProveedor(in codEP int, in emailP varchar(50), in descrip varchar (100), in codigoP int)
		begin
			update EmailProveedor EP
				set
                EP.codigoEmailProveedor = codEP,
				EP.emailProveedor = emailP,
				EP.descripcion = descrip,
				EP.codigoProveedor = codigoP
				where codigoEmailProveedor = codEP;
		end $$
delimiter ;   

call sp_editarEmailProveedor(1, 'crhistophergomez@gmail.com', 'Correo Personal', 1);
call sp_listarEmailProveedor();

-- Agregar telefono Proveedor

Delimiter $$
	create procedure sp_agregarTelefonoProveedor (in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar (8), observaciones varchar (45), in codigoProveedor int)
		begin
		insert into TelefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) 
        values (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor);
	end $$
Delimiter ;

call sp_agregarTelefonoProveedor(1, '11111111', '33333333', 'El primero es numero de casa', 1);
call sp_agregarTelefonoProveedor(2, '22222222', '44444444', 'El primero es numero personal', 1);

-- Listar telefono Proveedor

Delimiter $$
	create procedure sp_listarTelefonoProveedor()
		begin
			select
			TP.codigoTelefonoProveedor,
			TP.numeroPrincipal,
			TP.numeroSecundario,
			TP.observaciones,
            TP.codigoProveedor
		from TelefonoProveedor TP;
	end $$
Delimiter ;

call sp_listarTelefonoProveedor();

-- Buscar telefono Proveedor

Delimiter $$
	Create procedure sp_buscarTelefonoProvedor (in codTP int)
		begin
			select
			TP.codigoTelefonoProveedor,
			TP.numeroPrincipal,
			TP.numeroSecundario,
			TP.observaciones,
            TP.codigoProveedor
			from TelefonoProveedor TP
			where codigoTelefonoProveedor = codTP;
	end $$
delimiter ;

call sp_buscarTelefonoProvedor(1);

-- Eliminar telefono Proveedor 

Delimiter $$
	create procedure sp_eliminarTelefonoProveedor(in codTP int)
		begin
			delete from TelefonoProveedor
			where codigoTelefonoProveedor = codTP;
	end $$
delimiter ;

call sp_eliminarTelefonoProveedor(2);
call sp_listarTelefonoProveedor();

-- Editar telefono Proveedor 

Delimiter $$
	create procedure sp_editarTelefonoProveedor(in codTP int, in numUno varchar(8), in numDos varchar (8), in obser varchar(45), in codigoP int)
		begin
			update TelefonoProveedor TP
				set
                TP.codigoTelefonoProveedor = codTP,
				TP.numeroPrincipal = numUno,
                TP.numeroSecundario = numDos,
				TP.observaciones = obser,
				TP.codigoProveedor = codigoP
				where codigoTelefonoProveedor = codTP;
		end $$
delimiter ;   

call sp_editarTelefonoProveedor(1, '20230302', '20240302', 'El primer numero es personal', 1);
call sp_listarTelefonoProveedor();

-- Agregar detalle Compra 

Delimiter $$
	create procedure sp_agregarDetalleCompra (in codigoDetalleCompra int, in costoUnitario decimal(10, 2), in cantidad int, in codigoProducto int, in numeroDocumento int)
		begin
		insert into DetalleCompra (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento) 
        values (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento);
	end $$
Delimiter ;

call sp_agregarDetalleCompra(1, 12.00, 100, 2, 2);
call sp_agregarDetalleCompra(2, 15.12, 150, 2, 2);

-- Listar detalle Compra

Delimiter $$
	create procedure sp_listarDetalleCompra()
		begin
			select
			DC.codigoDetalleCompra,
			DC.costoUnitario,
			DC.cantidad,
			DC.codigoProducto,
            DC.numeroDocumento
		from DetalleCompra DC;
	end $$
Delimiter ;

call sp_listarDetalleCompra();

-- Buscar detalle Compra 

Delimiter $$
	create procedure sp_buscarDetalleCompra (in codDC int)
		begin
			select
			DC.codigoDetalleCompra,
			DC.costoUnitario,
			DC.cantidad,
			DC.codigoProducto,
            DC.numeroDocumento
			from DetalleCompra DC
			where codigoDetalleCompra = codDC;
	end $$
delimiter ;

call sp_buscarDetalleCompra(1);

-- Eliminar detalle Compra

Delimiter $$
	create procedure sp_eliminarDetalleCompra(in codDC int)
		begin
			delete from DetalleCompra
			where codigoDetalleCompra = codDC;
	end $$
delimiter ;

call sp_eliminarDetalleCompra(2);

-- Editar detalle Compra

Delimiter $$
	create procedure sp_editarDetalleCompra(in codDC int, in costoU decimal(10, 2), in cantidadProductos int, in codProducto int, in numDocumento int)
		begin
			update DetalleCompra DC
				set
                DC.codigoDetalleCompra = codDC,
                DC.costoUnitario = costoU,
				DC.cantidad = cantidadProductos,
				DC.codigoProducto = codProducto,
                DC.numeroDocumento = numDocumento
				where codigoDetalleCompra = codDC;
		end $$
delimiter ;   

call sp_editarDetalleCompra(1, 12.70, 199, 2, 2);
call sp_listarDetalleCompra();

-- Agregar Empleado

Delimiter $$
	create procedure sp_agregarEmpleados (in codigoEmpleado int, in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10, 2), in direccion varchar(150),
    in turno varchar(15), in codigoCargoEmpleado int)
		begin
		insert into Empleados (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado) 
        values (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado);
	end $$
Delimiter ;

call sp_agregarEmpleados(1, 'Crhistopher', 'Gomez' , 3500.00, 'Zona 13', 'Matutino', 1);
call sp_agregarEmpleados(2, 'Alexander', 'Rojas' , 4500.00, 'Zona 18', 'Matutino', 1);

-- Listar Empleado

Delimiter $$
	create procedure sp_listarEmpleados()
		begin
			select
			e.codigoEmpleado,
			e.nombresEmpleado,
			e.apellidosEmpleado,
			e.sueldo,
            e.direccion,
            e.turno,
            e.codigoCargoEmpleado
		from Empleados e;
	end $$
Delimiter ;

call sp_listarEmpleados();

-- Buscar Empleado

Delimiter $$
	create procedure sp_buscarEmpleados (in codEmpleado int)
		begin
			select
			e.codigoEmpleado,
			e.nombresEmpleado,
			e.apellidosEmpleado,
			e.sueldo,
            e.direccion,
            e.turno,
            e.codigoCargoEmpleado
			from Empleados e
			where codigoEmpleado = codEmpleado;
	end $$
delimiter ;

call sp_buscarEmpleados(1);

-- Eliminar Empleado

Delimiter $$
	create procedure sp_eliminarEmpleados(in codEmpleado int)
		begin
			delete from Empleados
			where codigoEmpleado = codEmpleado;
	end $$
delimiter ;

call sp_eliminarEmpleados(2);
call sp_listarEmpleados();

-- Editar Empleado

Delimiter $$
	Create procedure sp_editarEmpleados(in codEmpleado int, in nombreE varchar(50), in apellidoE varchar(50), in salario decimal(10, 2), in direccionE varchar(150), 
    in jornada varchar(15), in codCE int)
		begin
			update Empleados e
				set
                e.codigoEmpleado = codEmpleado,
                e.nombresEmpleado = nombreE,
				e.apellidosEmpleado = apellidoE,
				e.sueldo = salario,
                e.direccion = direccionE,
                e.turno = jornada,
                e.codigoCargoEmpleado = codCE
				where codigoEmpleado = codEmpleado;
		end $$
delimiter ;   

call sp_editarEmpleados(1, 'Cristian', 'Gonzalez' , 7000.00, 'Zona 10', 'Matutino', 1);
call sp_ListarEmpleados();


-- Agregar Factura

Delimiter $$
	create procedure sp_agregarFactura (in numeroFactura int, in estado varchar(50), in totalFactura decimal(10, 2), in fechaFactura date, 
    in codigoCliente int, in codigoEmpleado int)
		begin
		insert into Factura (numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado) 
        values (numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado);
	end $$
Delimiter ;

call sp_agregarFactura(1, 'Pendiente', 1200.00, '2024-02-12', 1, 1);
call sp_agregarFactura(2, 'Pagada', 2268, '2024-04-24', 1, 1);

-- Listar Factura 

Delimiter $$
	create procedure sp_listarFactura()
		begin
			select
			fac.numeroFactura,
			fac.estado,
			fac.totalFactura,
			fac.fechaFactura,
            fac.codigoCliente,
            fac.codigoEmpleado
		from Factura fac;
	end $$
Delimiter ;

call sp_listarFactura();

-- Buscar Factura 

Delimiter $$
	create procedure sp_buscarFactura (in numFac int)
		begin
			select
			fac.numeroFactura,
			fac.estado,
			fac.totalFactura,
			fac.fechaFactura,
            fac.codigoCliente,
            fac.codigoEmpleado
			from Factura fac
			where numeroFactura = numFac;
	end $$
delimiter ;

call sp_buscarFactura(1);

-- Eliminar Factura 

Delimiter $$
	create procedure sp_eliminarFactura(in numFac int)
		begin
			delete from Factura
			where numeroFactura = numFac;
	end $$
delimiter ;

call sp_eliminarFactura(2);
call sp_listarFactura();

-- Editar Factura

Delimiter $$
	create procedure sp_editarFactura(in numFac int, in estatus varchar(50), in totalF decimal(10, 2), in fechaF date, in codCliente int, in codEmpleado int)
		begin
			update Factura fac
				set
                fac.numeroFactura = numFac,
                fac.estado = estatus,
				fac.totalFactura = totalF,
				fac.fechaFactura = fechaF,
                fac.codigoCliente = codCliente,
                fac.codigoEmpleado = codEmpleado
				where numeroFactura = numFac;
		end $$
delimiter ;   

call sp_editarFactura(1, 'Pagada', 2527.3, '2024-05-20', 1, 1);
call sp_listarFactura();

-- Agregar detalle Factura

Delimiter $$
	create procedure sp_agregarDetalleFactura (in codigoDetalleFactura int, in precioUnitario decimal(10, 2), in cantidad int, in numeroFactura int, in codigoProducto int)
		begin
		insert into DetalleFactura (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto) 
        values (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto);
	end $$
Delimiter ;

call sp_agregarDetalleFactura(1, 12.5, 50, 1, 2);
call sp_agregarDetalleFactura(2, 15.50, 10, 1, 2);

-- Listar detalle Factura

Delimiter $$
	create procedure sp_listarDetalleFactura()
		begin
			select
			DF.codigoDetalleFactura,
			DF.precioUnitario,
			DF.cantidad,
			DF.numeroFactura,
            DF.codigoProducto
		from DetalleFactura DF;
	end $$
Delimiter ;

call sp_listarDetalleFactura();

-- Buscar detalle Factura

Delimiter $$
	create procedure sp_buscarDetalleFactura (in numDF int)
		begin
			select
			DF.codigoDetalleFactura,
			DF.precioUnitario,
			DF.cantidad,
			DF.numeroFactura,
            DF.codigoProducto
			from DetalleFactura DF
			where codigoDetalleFactura = numDF;
	end $$
delimiter ;

call sp_buscarDetalleFactura(1);

-- Eliminar detalle Factura

Delimiter $$
	create procedure sp_eliminarDetalleFactura(in numDF int)
		begin
			delete from DetalleFactura
			where codigoDetalleFactura = numDF;
	end $$
delimiter ;

call sp_eliminarDetalleFactura(2);
call sp_listarDetalleFactura();

-- Editar detalle Factura

Delimiter $$
	Create procedure sp_editarDetalleFactura(in numDF int, in precioU decimal(10, 2), in cant int, in numFactura int, in codProducto int)
		begin
			update DetalleFactura DF
				set
                DF.codigoDetalleFactura = numDF,
                DF.precioUnitario = precioU,
				DF.cantidad = cant,
				DF.numeroFactura = numFactura,
                DF.codigoProducto = codProducto
				where codigoDetalleFactura = numDF;
		end $$
delimiter ;   

call sp_editarDetalleFactura(1, 25.02, 12, 1, 2);
call sp_listarDetalleFactura();

set global time_zone = '-6:00'