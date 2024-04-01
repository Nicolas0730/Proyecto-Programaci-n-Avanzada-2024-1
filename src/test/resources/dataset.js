db = connect( 'mongodb://root:example@localhost:27017/proyecto?authSource=admin' );
db.clientes.insertMany([
    {
        _id: 'Cliente1',
        nickname: 'juanito',
        ciudad: 'Armenia',
        urlFotoPerfil: 'mi foto',
        direccion:'Quimbaya Mz 1 #12',
        correo: 'juan@email.com',
        contrasenia: 'mipassword',
        nombre: 'Juan',
        estadoCuenta: 'ACTIVO',
        _class: 'co.edu.uniquindio.unilocal.model.Usuario'
    },
    {
        _id: 'Cliente2',
        nickname: 'juanito2',
        ciudad: 'Armenia',
        urlFotoPerfil: 'mi foto',
        direccion:'Quimbaya Mz 1 #12',
        correo: 'juan@email.com',
        contrasenia: 'mipassword',
        nombre: 'Juan',
        estadoCuenta: 'ACTIVO',
        _class: 'co.edu.uniquindio.unilocal.model.Usuario'
    },
    {
        _id: 'Cliente3',
        nickname: 'juanito',
        ciudad: 'Armenia',
        urlFotoPerfil: 'mi foto',
        direccion:'Quimbaya Mz 1 #12',
        correo: 'juan@email.com',
        contrasenia: 'mipassword',
        nombre: 'Juan',
        estadoCuenta: 'ACTIVO',
        _class: 'co.edu.uniquindio.unilocal.model.Usuario'
    }
]);
db.negocios.insertMany([
    {
        _id: 'Negocio1',
        nombre: 'Restaurante Mexicano',
        descripcion: 'Restaurante de comida mexicana en Armenia',
        codigoCliente: 'Cliente1',
        ubicacion: {
            latitud: 4.540130,
            longitud: -75.665660
        },
        imagenes: ['imagen1', 'imagen2'],
        tipoNegocio: 'RESTAURANTE',
        horarios: [
            {
                dia: 'LUNES',
                horaInicio: '08:00',
                horaFin: '20:00'
            }
        ],
        telefonos: ['1234567', '7654321'],
        estado: 'ACTIVO',
        _class: 'co.edu.uniquindio.unilocal.model.Negocio'
    }
]);
db.comentarios.insertMany([
    {
        mensaje: "Excelente sitio, muy buena atención",
        fecha: new Date(),
        codigoCliente: 'Cliente1',
        codigoNegocio: 'Negocio1',
        calificacion: 5,
        _class: 'co.edu.uniquindio.unilocal.model.Comentario'
    }
]);