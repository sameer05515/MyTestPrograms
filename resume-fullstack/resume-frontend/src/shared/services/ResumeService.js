import http from '../http-common';

const getAll = () => {
  return http.get("/person/");
};

const get = (id) => {
  return http.get(`/person/${id}`);
};

const getResume = (personId, resumeId) => {
  return http.get(`/person/${personId}/resume/${resumeId}`);
};

// const create = (data) => {
//   return http.post("/tutorials", data);
// };

// const update = (id, data) => {
//   return http.put(`/tutorials/${id}`, data);
// };

// const remove = (id) => {
//   return http.delete(`/tutorials/${id}`);
// };

// const removeAll = () => {
//   return http.delete(`/tutorials`);
// };

// const findByTitle = (title) => {
//   return http.get(`/tutorials?title=${title}`);
// };

const ResumeService = {
  getAll,
  get,
  getResume,
  // create,
  // update,
  // remove,
  // removeAll,
  // findByTitle,
};

export default ResumeService;
