module.exports = app => {
    const Whisky = require("../controllers/whisky.controller.js");

    var router = require("express").Router();

    // Create a new whisky
    router.post("/", Whisky.create);

    // Retrieve all whiskys
    router.get("/", Whisky.findAll);

    // Retrieve a single whisky with id
    router.get("/:id", Whisky.findOne);

    // Update a whisky with id
    router.put("/:id", Whisky.update);

    // Delete a whisky with id
    router.delete("/:id", Whisky.delete);



    app.use('/api/whisky', router);
};