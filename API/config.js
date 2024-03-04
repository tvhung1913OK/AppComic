const multer = require("multer");
const cloudinary = require("cloudinary").v2;
const { CloudinaryStorage } = require("multer-storage-cloudinary");

cloudinary.config({
  cloud_name: 'dow20wd2t',
  api_key: '653532811959946',
  api_secret: 'Yzh2sXSgvBdHIqJcG36qHeLSWgI',
});

const storage = new CloudinaryStorage({
  cloudinary: cloudinary,
  params: {
    folder: 'avatars',
    public_id: (req, file) => `${file.originalname}_${Date.now()}`,
  },
});
const storageStory = new CloudinaryStorage({
    cloudinary: cloudinary,
    params: {
      folder: "story_images", 
      public_id: (req, file) => `${file.originalname}_${Date.now()}`, 
    },
  });
const upload = multer({ storage: storage });
const uploadStory = multer({ storage: storageStory });

module.exports = { upload,uploadStory };
