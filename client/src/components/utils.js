import joi from "joi";

const loginValidation = (username, password) => {
  const schema = joi.object({
    username: joi.string().min(6).required(),
    password: joi.string().min(6).required(),
  });

  const result = schema.validate({ username: username, password: password });
  return result.error == null;
};

const signupValidation = (
  lastname,
  firstname,
  username,
  email,
  password,
  confirmPassword
) => {
  const schema = joi.object({
    username: joi.string().min(6).required(),
    password: joi.string().min(6).required(),
    lastname: joi.string().min(6).required(),
    firstname: joi.string().min(6).required(),
    email: joi.string().email({ tlds: { allow: false } }).min(6),
    confirmPassword: joi.string().min(6).required(),
  });
  const result = schema.validate({
    username: username,
    email: email,
    confirmPassword: confirmPassword,
    password: password,
    firstname: firstname,
    lastname: lastname,
  });

  return result.error;
};
const isLoggedIn = () => {
  const user = localStorage.getItem("userId");
  
  return user != null;
};
export { loginValidation, signupValidation, isLoggedIn };
