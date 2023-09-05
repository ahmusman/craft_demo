
#include <iostream>
#include <memory>
#include <string>

#include "absl/flags/flag.h"
#include "absl/flags/parse.h"
#include "absl/strings/str_format.h"

#include <grpcpp/ext/proto_server_reflection_plugin.h>
#include <grpcpp/grpcpp.h>
#include <grpcpp/health_check_service_interface.h>


#include "person.grpc.pb.h"

using grpc::Server;
using grpc::ServerBuilder;
using grpc::ServerContext;
using grpc::Status;
using setter::UserRequest;
using setter::Setter;
using setter::UserReply;

ABSL_FLAG(uint16_t, port, 50051, "Server port for the service");


class SetterServiceImpl final : public Setter::Service{

  Status SetUser(ServerContext* context, const UserRequest* request,
                    UserReply* reply) override{

                  std::cout << "User: "<< request->firstname() << 
                  "has been set at" << request->updatedat() <<std::endl;
                  return Status::OK;

        }
};

void RunServer(uint16_t port) {
  std::string server_address = absl::StrFormat("0.0.0.0:%d", port);

  SetterServiceImpl service;

  grpc::EnableDefaultHealthCheckService(true);
  grpc::reflection::InitProtoReflectionServerBuilderPlugin();
  ServerBuilder builder;
  // Listen on the given address without any authentication mechanism.
  builder.AddListeningPort(server_address, grpc::InsecureServerCredentials());
  // Register "service" as the instance through which we'll communicate with
  // clients. In this case it corresponds to an *synchronous* service.
  builder.RegisterService(&service);
  // Finally assemble the server.
  std::unique_ptr<Server> server(builder.BuildAndStart());
  std::cout << "Server listening on " << server_address << std::endl;

  // Wait for the server to shutdown. Note that some other thread must be
  // responsible for shutting down the server for this call to ever return.
  server->Wait();
}

int main(int argc, char** argv) {
  absl::ParseCommandLine(argc, argv);
  RunServer(absl::GetFlag(FLAGS_port));
  return 0;
}