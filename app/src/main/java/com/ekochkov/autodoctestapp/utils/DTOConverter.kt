package com.ekochkov.autodoctestapp.utils

import com.ekochkov.autodoctestapp.data.entity.*

object DTOConverter {

    fun fromSearchRepositoriesDTOToRepositoryList(searchRepositoriesDTO: SearchRepositoriesDTO): List<Repository> {
        val list = arrayListOf<Repository>()
        searchRepositoriesDTO.items.forEach { itemDTO ->
            list.add(fromItemDTOToRepository(itemDTO))
        }
        return list
    }

    fun fromOwnerDTOToOwner(ownerDTO: OwnerDTO): Owner {
        return Owner(
            id = ownerDTO.id?: 0,
            avatarUrl = ownerDTO.avatar_url?: "empty",
            login = ownerDTO.login?: "empty"
        )
    }

    fun fromUserDTOToUser(userDTO: UserDTO): User {
        return User(
            nickname = userDTO.name?: "empty",
            bio = userDTO.bio?: "empty",
            avatarUrl = userDTO.avatar_url?: "empty",
            followers = userDTO.followers?: 0,
            following = userDTO.following?: 0,
            blog = userDTO.blog?: "empty",
            twitter = userDTO.twitter_username?: "empty"
        )
    }

    private fun fromItemDTOToRepository(itemDTO: ItemDTO): Repository {
        return Repository(
            name = itemDTO.name?: "empty",
            description = itemDTO.description?: "empty",
            language = itemDTO.language?: "empty",
            languages_url = itemDTO.languages_url?: "empty",
            owner = fromOwnerDTOToOwner(itemDTO.owner),
            updated_at = itemDTO.updated_at?: "empty",
            stargazers_count = itemDTO.stargazers_count?: 0,
        )
    }
}