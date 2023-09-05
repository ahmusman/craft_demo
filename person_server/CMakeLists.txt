# Copyright 2018 gRPC authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# cmake build file for C++ helloworld example.
# Assumes protobuf and gRPC have been installed using cmake.
# See cmake_externalproject/CMakeLists.txt for all-in-one cmake build
# that automatically builds all the dependencies before building helloworld.

cmake_minimum_required(VERSION 3.8)

project(Person C CXX)

include(cmake/common.cmake)

# Proto file
get_filename_component(p_proto "./proto/person.proto" ABSOLUTE)
get_filename_component(p_proto_path "${p_proto}" PATH)

# Generated sources
set(p_proto_srcs "${CMAKE_CURRENT_BINARY_DIR}/person.pb.cc")
set(p_proto_hdrs "${CMAKE_CURRENT_BINARY_DIR}/person.pb.h")
set(p_grpc_srcs "${CMAKE_CURRENT_BINARY_DIR}/person.grpc.pb.cc")
set(p_grpc_hdrs "${CMAKE_CURRENT_BINARY_DIR}/person.grpc.pb.h")
add_custom_command(
      OUTPUT "${p_proto_srcs}" "${p_proto_hdrs}" "${p_grpc_srcs}" "${p_grpc_hdrs}"
      COMMAND ${_PROTOBUF_PROTOC}
      ARGS --grpc_out "${CMAKE_CURRENT_BINARY_DIR}"
        --cpp_out "${CMAKE_CURRENT_BINARY_DIR}"
        -I "${p_proto_path}"
        --plugin=protoc-gen-grpc="${_GRPC_CPP_PLUGIN_EXECUTABLE}"
        "${p_proto}"
      DEPENDS "${p_proto}")

# Include generated *.pb.h files
include_directories("${CMAKE_CURRENT_BINARY_DIR}")

# p_grpc_proto
add_library(p_grpc_proto
  ${p_grpc_srcs}
  ${p_grpc_hdrs}
  ${p_proto_srcs}
  ${p_proto_hdrs})
target_link_libraries(p_grpc_proto
  ${_REFLECTION}
  ${_GRPC_GRPCPP}
  ${_PROTOBUF_LIBPROTOBUF})

# Targets greeter_[async_](client|server)
  add_executable(person_server "server/person_server.cc")
  target_link_libraries(person_server
    p_grpc_proto
    absl::flags
    absl::flags_parse
    ${_REFLECTION}
    ${_GRPC_GRPCPP}
    ${_PROTOBUF_LIBPROTOBUF})
