module.exports = (sequelize, Sequelize) => {
    const Whisky = sequelize.define("whisky", {
        ID_whis: {
            type: Sequelize.INTEGER,
            primaryKey: true
        },
        Nombre: {
            type: Sequelize.STRING
        },
        Procedencia: {
            type: Sequelize.STRING
        },
        descripcion: {
            type: Sequelize.STRING
        },
        image: {
            type: Sequelize.STRING
        }
    }, { timestamps: false });

    return Whisky;
};