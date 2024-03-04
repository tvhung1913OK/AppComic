// routes/story.route.js
const router = require("express").Router();
const ctrl = require("../controllers/story.controller");
const { uploadStory } = require("../config");

router.get("/", ctrl.GetAll);
router.get("/getbyname/:ten", ctrl.GetElementByName);
router.get("/:id", ctrl.GetElement);
router.post("/create", uploadStory.fields([
    { name: "background", maxCount: 1 },
    { name: "images", maxCount: 30 }
  ]), ctrl.CreateElement);
router.put("/update/:id", ctrl.UpdateElement);
router.put("/delete/:id", ctrl.DeleteElement);
router.put("/delete", ctrl.DeleteAll);

module.exports = router;