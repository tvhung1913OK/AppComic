const express = require("express");
const app = express();
const apiUserRouter = require("./routers/user.router");
const exphbs = require("express-handlebars");
const apiCommentRouter = require("./routers/comment.router");

const apiStoryRouter = require("./routers/story.router");

const apiRouter = require("./routers/api.router");

const bodyParser = require("body-parser");
const port = 9000;
app.engine("hbs", exphbs.engine({ extname: "hbs" }));
app.set("view engine", "hbs");
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use("/api/user", apiUserRouter);
app.use(express.static('views'));
app.use("/api/comment", apiCommentRouter);

app.use("/api/story", apiStoryRouter);

app.use("/api", apiRouter);
app.get("/login", (req, res) => {
  res.render("login");
});
app.get("/register", (req, res) => {
  res.render("register");
});
app.get("/story", (req, res) => {
  res.render("create-story");
});
app.listen(port, (err) => {
  if (err) throw err;

  console.log(" Run port " + port);
});
