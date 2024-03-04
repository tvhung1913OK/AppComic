const router = require("express").Router();
const ctrl = require("../controllers/api.controller");
const { upload } = require("../config"); 

router.post("/login", ctrl.Login);
router.post("/register", upload.single("avatar"), ctrl.Register);
// router.post("/register", ctrl.Register);
router.get("/comment/story/:id", ctrl.GetCommentWithStoryID);

module.exports = router;
