db = connect( 'mongodb://root:example@localhost:27017/unilocalTest?authSource=admin' );

db.comentario.insertMany([
    {
        descripcion: "Excelente sitio, muy buena atención",
        calificacion: 5,
        idUsuario: 'Usuario1',
        idNegocio: 'Negocio1',
        respuesta: '',
        _class: 'co.edu.uniquindio.unilocal.model.Comentario'
    },
    {
        descripcion: "Excelente sitio, muy buena atención",
        calificacion: 4,
        idUsuario: 'Usuario2',
        idNegocio: 'Negocio2',
        respuesta: '',
        _class: 'co.edu.uniquindio.unilocal.model.Comentario'
    },
    {
        descripcion: "Mal servicio",
        calificacion: 3,
        idUsuario: 'Usuario5',
        idNegocio: 'Negocio3',
        respuesta: '',
        _class: 'co.edu.uniquindio.unilocal.model.Comentario'
    },
    {
        descripcion: "Excelente sitio, muy buena atención",
        calificacion: 5,
        idUsuario: 'Usuario3',
        idNegocio: 'Negocio4',
        respuesta: '',
        _class: 'co.edu.uniquindio.unilocal.model.Comentario'
    },
    {
        descripcion: "Excelente sitio, muy buena atención",
        calificacion: 5,
        idUsuario: 'Usuario5',
        idNegocio: 'Negocio5',
        respuesta: '',
        _class: 'co.edu.uniquindio.unilocal.model.Comentario'
    }
]);
