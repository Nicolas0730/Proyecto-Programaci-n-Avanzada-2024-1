db = connect( 'mongodb://root:example@localhost:27017/unilocalTest?authSource=admin' );
db.usuario.insertMany([
    {
        _id: 'Cliente1',
        nickname: 'juanito',
        ciudad: 'ARMENIA',
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
        ciudad: 'ARMENIA',
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
        ciudad: 'ARMENIA',
        urlFotoPerfil: 'mi foto',
        direccion:'Quimbaya Mz 1 #12',
        correo: 'juan@email.com',
        contrasenia: 'mipassword',
        nombre: 'Juan',
        estadoCuenta: 'ACTIVO',
        _class: 'co.edu.uniquindio.unilocal.model.Usuario'
    },
    {
        _id: 'Usuario4',
        nickname: 'juanito4',
        ciudad: 'ARMENIA',
        urlFotoPerfil: 'mi foto',
        direccion: 'Quimbaya Mz 1 #12',
        correo: 'juan@email.com',
        contrasenia: 'Mipassword4@',
        nombre: 'Juan',
        estadoCuenta: 'ACTIVO',
        _class: 'co.edu.uniquindio.unilocal.model.Usuario' },
    {
        _id: 'Usuario5',
        nickname: 'juanito5',
        ciudad: 'ARMENIA',
        urlFotoPerfil: 'mi foto',
        direccion: 'Quimbaya Mz 1 #12',
        correo: 'juan@email.com',
        contrasenia: 'Mipassword5@',
        nombre: 'Juan',
        estadoCuenta: 'ACTIVO',
        _class: 'co.edu.uniquindio.unilocal.model.Usuario' }
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
    },
    {
        _id: 'Negocio2',
        nombre: 'Restaurante La fogata',
        descripcion: 'Restaurante de 1963 con ambiente tranquilo y antiguo, platos de carnes tradicionales y bebidas.',
        codigoCliente: 'Cliente2',
        ubicacion: {
            latitud: 4.5539956,
            longitud:-75.6585289
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
    },
    {
        _id: 'Negocio3',
        nombre: 'Casa Quimbaya',
        descripcion: 'Este hotel tranquilo se encuentra a 12 minutos a pie del parque Vida, a 3 km del Museo del Oro Quimbaya y a 19 km del jardín botánico del Quindío.',
        codigoCliente: 'Cliente3',
        ubicacion: {
            latitud: 4.5538009,
            longitud: -75.6590702
        },
        imagenes: ['imagen1', 'imagen2'],
        tipoNegocio: 'HOTEL',
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
    },
    {
        _id: 'Negocio4',
        nombre: 'Museo del oro Quimbaya',
        descripcion: 'Museo del oro Quimbaya',
        codigoCliente: 'Cliente4',
        ubicacion: {
            latitud: 4.5603605,
            longitud: -75.6692269
        },
        imagenes: ['imagen1', 'imagen2'],
        tipoNegocio: 'MUSEO',
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
    },
    {
        _id: 'Negocio5',
        nombre: 'Sandwich Qbano',
        descripcion: 'Sandwich Qbano',
        codigoCliente: 'Cliente5',
        ubicacion: {
            latitud: 4.5594048,
            longitud: -75.6557529
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
    },
    {
        mensaje: "Excelente sitio, muy buena atención",
        fecha: new Date(),
        codigoCliente: 'Cliente2',
        codigoNegocio: 'Negocio2',
        calificacion: 4,
        _class: 'co.edu.uniquindio.unilocal.model.Comentario'
    },
    {
        mensaje: "Mal servicio",
        fecha: new Date(),
        codigoCliente: 'Cliente5',
        codigoNegocio: 'Negocio1',
        calificacion: 3,
        _class: 'co.edu.uniquindio.unilocal.model.Comentario'
    },
    {
        mensaje: "Excelente sitio, muy buena atención",
        fecha: new Date(),
        codigoCliente: 'Cliente3',
        codigoNegocio: 'Negocio4',
        calificacion: 5,
        _class: 'co.edu.uniquindio.unilocal.model.Comentario'
    },
    {
        mensaje: "Excelente sitio, muy buena atención",
        fecha: new Date(),
        codigoCliente: 'Cliente5',
        codigoNegocio: 'Negocio5',
        calificacion: 5,
        _class: 'co.edu.uniquindio.unilocal.model.Comentario'
    }
]);