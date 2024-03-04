const mongoose = require("mongoose");
const nameDB = "Comic";
mongoose
  .connect(
    `mongodb://127.0.0.1/${nameDB}`
  )
  .then(() => console.log("✅ Connected database from mongodb."))
  .catch((error) =>
    console.error(`❌ Connect database is failed with error which is ${error}`)
  );
module.exports = { mongoose };
