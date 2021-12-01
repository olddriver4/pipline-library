def triggers(string token, string trigger_branch) {
    triggers{
        GenericTrigger(
            genericVariables: [
            [key: 'branch', value: '$.ref']
            ],
            causeString: 'Triggered on $branch',
            token: token, //变更
            printContributedVariables: true,
            printPostContent: true,
            silentResponse: false,
            regexpFilterText: '$branch',
            regexpFilterExpression: '^refs/heads/(' + trigger_branch +')$' //变更
        )
    }
}