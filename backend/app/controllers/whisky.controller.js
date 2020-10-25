const db = require("../models");
const Whisky = db.whiskys;
const Op = db.Sequelize.Op;

// Create and Save a new whisky
// req --> request (contains the body)
exports.create = (req, res) => {
    // Validate request
    /*if (!req.body.ID_whis || !req.body.Nombre) {
        res.status(400).send({
            message: "Content can not be empty!"
        });
        return;
    }*/

    // Create a whisky
    const whisky = {
        ID_whis: req.body.ID_whis,
        Nombre: req.body.Nombre,
        Procedencia: req.body.Procedencia,
        descripcion: req.body.descripcion,
        image: req.body.image
    };

    // Save whisky in the database
    Whisky.create(whisky)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message: err.message || "Some error occurred while creating the whisky."
            });
        });
};

// Retrieve all whiskys from the database.
exports.findAll = (req, res) => {

    Whisky.findAll()
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message: err.message || "Some error occurred while retrieving whiskys."
            });
        });
};

// Find a single whisky with an id
exports.findOne = (req, res) => {
    let id = req.params.id;
    Whisky.findByPk(id)
        .then(data => {
            console.log("estos son los datos")
            console.log(data);
            if (!data) {
                res.status(400).send({
                    message: "No whisky found with that id"
                })
            }
            res.send(data);
            return;
        })
        .catch(err => {
            console.log(err.message);
            console.log("hola");
            res.status(500).send({
                message: err.message || "Some error occurred while retrieving whisky with id"
            });
            return;
        });
};

// Update a Tutorial by the id in the request
exports.update = (req, res) => {
    let id = req.body.id;


    const whisky = {
        ID_whis: req.body.brand,
        Nombre: req.body.model,
        Procedencia: req.body.model,
        descripcion: req.body.model,
        image: req.body.model
    };
    Whisky.update(id)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message: err.message || "Some error occurred while retrieving whiskys."
            });
        });
};

// Delete a Tutorial with the specified id in the request
exports.delete = (req, res) => {
    let id = req.params.id;
    Whisky.delete(id)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message: err.message || "Some error occurred while retrieving whiskys."
            });
        });
};